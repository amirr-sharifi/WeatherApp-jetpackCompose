package af.amir.weathermvi.presentation.cityFinder

import af.amir.weathermvi.R
import af.amir.weathermvi.domain.repository.LocationRepository
import af.amir.weathermvi.domain.util.Result
import af.amir.weathermvi.domain.util.WeatherError
import af.amir.weathermvi.presentation.util.LocationUtils
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityFinderViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val context: Application,
) : ViewModel() {

    private val _state = MutableStateFlow(CityFinderState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<CityFinderUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    private var searchJob: Job? = null

    fun proceedIntent(intent: CityFinderIntents) {
        when (intent) {
            is CityFinderIntents.OnCityClick -> {
                onCityClick(intent)
            }

            is CityFinderIntents.OnSearchQueryChange -> {
                onSearchQueryChange(intent)
            }

            is CityFinderIntents.RequestLocation -> {
                requestLocation(intent.context)

            }
        }
    }


    private fun requestLocation(context : Context) {
        viewModelScope.launch {
            if (!LocationUtils.isLocationPermissionGranted(context)){
                _uiEvent.send(CityFinderUIEvent.OnGetLocationPermission)
                return@launch
            }

            if (!LocationUtils.isGpsEnabled(context)){
                _uiEvent.send(CityFinderUIEvent.OnEnableGps)
                return@launch
            }

            repository.useDeviceLocation().collectLatest { result ->
                when (result) {
                    is Result.Error -> handleErrors(result.error)
                    is Result.Loading -> setLoadingTrue()
                    is Result.Success -> {
                        _uiEvent.send(CityFinderUIEvent.OnNavigateToWeatherScreen)
                        setLoadingFalse()
                    }
                }
            }
        }
    }

    private fun onCityClick(intent: CityFinderIntents.OnCityClick) {
        viewModelScope.launch {
            repository.getReverseCoddingData(
                lat = intent.location.lat,
                long = intent.location.long,
            ).collect { result ->
                when (result) {
                    is Result.Error -> handleErrors(result.error)

                    is Result.Loading -> setLoadingTrue()

                    is Result.Success -> {
                        _uiEvent.send(CityFinderUIEvent.OnNavigateToWeatherScreen)
                        setLoadingFalse()
                    }
                }
            }
        }
    }

    private fun onSearchQueryChange(intent: CityFinderIntents.OnSearchQueryChange) {
        _state.update {
            it.copy(searchQuery = intent.query)
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            getCityListing()
        }
    }

    private suspend fun getCityListing(
        query: String = _state.value.searchQuery,
    ) {
        repository.getCityListing(query).collect { result ->
            when (result) {
                is Result.Error -> {
                    handleErrors(result.error)
                }

                is Result.Loading -> setLoadingTrue()

                is Result.Success -> {
                    _state.update { it.copy(data = result.data!!, isLoading = false) }
                }
            }
        }


    }

    private fun setLoadingTrue(){_state.update { it.copy(isLoading = true) }}
    private fun setLoadingFalse(){_state.update { it.copy(isLoading = false) }}

    private suspend fun handleErrors(error: WeatherError) {
        setLoadingFalse()
        when (error) {
            is WeatherError.Network -> handleNetworkError(error)
            is WeatherError.Location -> handleLocationError(error)
        }
    }

    private suspend fun handleNetworkError(error: WeatherError.Network) {
        when (error) {
            WeatherError.Network.TimeOut -> _uiEvent.send(CityFinderUIEvent.ShowMessage(context.getString(R.string.connection_to_the_server_timed_out)))
            WeatherError.Network.Loading_Data ->  _uiEvent.send(CityFinderUIEvent.ShowMessage(context.getString(R.string.error_loading_data_from_the_network)))
            WeatherError.Network.Network_Is_Off -> _uiEvent.send(CityFinderUIEvent.ShowMessage(context.getString(R.string.internet_is_turned_off)))
        }
    }

    private suspend fun handleLocationError(error: WeatherError.Location) {
        when (error) {
          /*  WeatherError.Location.Not_Permission_Granted -> _uiEvent.send(CityFinderUIEvent.ShowMessage(context.getString(R.string.location_permission_is_not_granted)))

            WeatherError.Location.Location_Is_Off -> _uiEvent.send(CityFinderUIEvent.ShowMessage(context.getString(R.string.gps_is_turned_off)))
*/
            WeatherError.Location.Cant_Reach ->_uiEvent.send(CityFinderUIEvent.ShowMessage(context.getString(R.string.location_is_not_reachable)))
            else->Unit
        }
    }


}
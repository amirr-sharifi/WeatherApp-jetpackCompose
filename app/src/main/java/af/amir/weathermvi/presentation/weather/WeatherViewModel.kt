package af.amir.weathermvi.presentation.weather

import af.amir.weathermvi.R
import af.amir.weathermvi.domain.usecase.GetLocationFromDBUseCase
import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.domain.repository.WeatherRepository
import af.amir.weathermvi.domain.util.Result
import af.amir.weathermvi.domain.util.WeatherError
import af.amir.weathermvi.domain.model.weather.WeatherInfo
import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val context: Application,
    currentLocation: GetLocationFromDBUseCase,
) : ViewModel() {


    private var _location: LocationInfo? = currentLocation.invoke()


    private var _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    private var weatherInfo: WeatherInfo? = null

    private val _uiEvents = Channel<WeatherUiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            _location?.let {
                getWeatherInfo(it)
                refreshWeatherFromRemote(it)
            }
        }
    }

    fun proceedIntents(intent: WeatherIntents) {
        when (intent) {
            is WeatherIntents.ChangeSelectedIndex -> onHourlyDetailSelectedIndexChange(intent.index)
            WeatherIntents.Refresh -> {
                _location?.let { locationInfo ->
                    refreshWeatherFromRemote(locationInfo)
                    _state.update {
                        it.copy(isRefreshing = true)
                    }
                }
            }

            WeatherIntents.DismissBottomSheet -> {
                _state.update {
                    it.copy(showingBottomSheet = false)
                }
            }

            WeatherIntents.EditCurrentLocation -> {
                viewModelScope.launch {
                    _location?.let {
                        it.cityName?.let { cityName ->
                            _uiEvents.send(WeatherUiEvents.EditLocation(cityName))
                        }
                    }
                }
            }
        }
    }


    private fun onHourlyDetailSelectedIndexChange(index: Int) {
        viewModelScope.launch {
            when (index) {
                0 -> {
                    _state.update {
                        it.copy(
                            selectedDayIndex = 0,
                            futureHourlyWeatherData = weatherInfo!!.weatherDataPerDay[0]
                                ?: emptyList()
                        )
                    }
                }

                1 -> {
                    _state.update {
                        it.copy(
                            selectedDayIndex = 1,
                            futureHourlyWeatherData = weatherInfo!!.weatherDataPerDay[1]
                                ?: emptyList()
                        )
                    }
                }

                else -> {
                    _state.update {
                        it.copy(showingBottomSheet = true)
                    }
                }
            }
        }
    }

    private fun getWeatherInfo(location: LocationInfo) {
        repository.getWeatherData(location).onEach {
            handleLocalDataResult(it)
        }.launchIn(viewModelScope)
    }

    private fun refreshWeatherFromRemote(location: LocationInfo) {
        repository.fetchWeatherDataFromRemote(location).onEach {
            handleRemoteDataResult(it)
        }.launchIn(viewModelScope)
    }


    private suspend fun handleLocalDataResult(result: Result<WeatherInfo, WeatherError>) {

        when (result) {
            is Result.Error -> {
                _state.update { it.copy(localLoading = false) }
                handleError(result.error)
            }

            is Result.Loading -> {
                _state.update {
                    it.copy(localLoading = true)
                }

            }

            is Result.Success -> {

                result.data?.let { info ->
                    weatherInfo = info
                    _state.update {
                        it.copy(
                            cityName = info.cityName,
                            currentWeatherData = info.currentWeather,
                            weatherDataPerDay = info.weatherDataPerDay,
                            futureHourlyWeatherData = info.weatherDataPerDay[it.selectedDayIndex]
                                ?: emptyList(),
                            localLoading = false
                        )
                    }
                }
            }

        }
    }

    private suspend fun handleRemoteDataResult(result: Result<WeatherInfo, WeatherError>) {
        when (result) {
            is Result.Error -> {
                _state.update { it.copy(isRefreshing = false) }
                handleError(result.error)
            }

            is Result.Loading -> {
                _uiEvents.send(
                    WeatherUiEvents.ShowMessage(
                        ContextCompat.getString(
                            context,
                            R.string.updating
                        )
                    )
                )
                _state.update {
                    it.copy(isRefreshing = true)
                }

            }

            is Result.Success -> {
                _uiEvents.send(
                    WeatherUiEvents.ShowMessage(
                        ContextCompat.getString(
                            context,
                            R.string.successfully_updated
                        )
                    )
                )


                result.data?.let { info ->
                    weatherInfo = info
                    _state.update {
                        it.copy(
                            cityName = info.cityName,
                            currentWeatherData = info.currentWeather,
                            weatherDataPerDay = info.weatherDataPerDay,
                            futureHourlyWeatherData = info.weatherDataPerDay[it.selectedDayIndex]
                                ?: emptyList(),
                            isRefreshing = false
                        )
                    }
                }
            }

        }
    }

    private suspend fun handleError(error: WeatherError) {
        when (error) {
            is WeatherError.Network -> {
                handleNetworkError(error)
            }

            is WeatherError.UnknownError -> {
                _uiEvents.send(WeatherUiEvents.ShowMessage(error.message))
            }
        }
    }


    private suspend fun handleNetworkError(error: WeatherError.Network) {
        when (error) {
            WeatherError.Network.TimeOut -> _uiEvents.send(
                WeatherUiEvents.ShowMessage(
                    context.getString(
                        R.string.connection_to_the_server_timed_out
                    )
                )
            )

            WeatherError.Network.Loading_Data -> _uiEvents.send(
                WeatherUiEvents.ShowMessage(
                    context.getString(
                        R.string.error_loading_data_from_the_network
                    )
                )
            )

            WeatherError.Network.Network_Is_Off -> _uiEvents.send(
                WeatherUiEvents.ShowMessage(
                    context.getString(R.string.internet_is_turned_off)
                )
            )
        }
    }


}
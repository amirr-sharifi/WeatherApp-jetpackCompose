package af.amir.weathermvi

import af.amir.weathermvi.domain.usecase.GetLocationFromDBUseCase
import af.amir.weathermvi.navigation.NavDestinations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MainUiState(
    val isReady : Boolean = false,
    val startDestination : String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentLocation: GetLocationFromDBUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkIfLocationExits()
    }

    private fun checkIfLocationExits(){
        val hasLocation = currentLocation.invoke() != null
        _uiState.update {
            it.copy(
                startDestination = if (hasLocation) NavDestinations.Weather.route else NavDestinations.LocationWelcome.route,
                isReady = true
            )
        }
    }


}


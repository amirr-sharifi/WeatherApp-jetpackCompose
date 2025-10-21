package af.amir.weathermvi.presentation.cityFinder

sealed class CityFinderUIEvent {
    data object OnGetLocationPermission : CityFinderUIEvent()
    data object OnEnableGps : CityFinderUIEvent()
    data object OnNavigateToWeatherScreen : CityFinderUIEvent()
    data class ShowMessage(val message: String) : CityFinderUIEvent()
}
package af.amir.weathermvi.presentation.cityFinder

import af.amir.weathermvi.domain.model.location.LocationInfo

data class CityFinderState(
    val data: List<LocationInfo> = emptyList(),
    val searchQuery : String ="",
    val isLoading : Boolean = false,
)

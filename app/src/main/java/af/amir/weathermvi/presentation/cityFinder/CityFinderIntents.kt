package af.amir.weathermvi.presentation.cityFinder

import af.amir.weathermvi.domain.model.location.LocationInfo
import android.content.Context

sealed class CityFinderIntents {
    data class OnSearchQueryChange(val query: String) : CityFinderIntents()
    data class RequestLocation(val context : Context) : CityFinderIntents()
    data class OnCityClick(val location:LocationInfo ): CityFinderIntents()



}
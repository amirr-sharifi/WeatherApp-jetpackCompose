package af.amir.weathermvi.domain.tracker

import af.amir.weathermvi.domain.model.location.LocationInfo


interface LocationTracker {
    suspend fun getCurrentLocation() : LocationInfo?
}
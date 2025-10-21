package af.amir.weathermvi.domain.repository

import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.domain.util.Result
import af.amir.weathermvi.domain.util.WeatherError
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getCityListing(query: String): Flow<Result<List<LocationInfo>, WeatherError>>
    suspend fun getReverseCoddingData(
        lat: Double,
        long: Double,
    ): Flow<Result<LocationInfo, WeatherError>>

     fun useDeviceLocation(): Flow<Result<LocationInfo, WeatherError>>

     fun getLocationFromDb () : LocationInfo?

}
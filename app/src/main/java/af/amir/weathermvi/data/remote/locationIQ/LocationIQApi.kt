package af.amir.weathermvi.data.remote.locationIQ

import af.amir.weathermvi.BuildConfig
import af.amir.weathermvi.data.remote.locationIQ.dto.LocationAutoCompleteDto
import af.amir.weathermvi.data.remote.locationIQ.dto.ReverseCodingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationIQApi {

    @GET("v1/reverse")
    suspend fun getReverseCoddingData(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("format") format : String = "json",
        @Query("key") apiKey: String = API_KEY
    ) : ReverseCodingDto


    @GET("v1/autocomplete")
    suspend fun getLocationAutoCompleteList(
        @Query("q") query: String,
        @Query("limit") limit :Int = 5,
        @Query("dedupe") dedupe :Int = 1,
        @Query("key") apiKey: String = API_KEY
    ):ArrayList<LocationAutoCompleteDto>

    companion object {
        const val API_KEY = BuildConfig.LOCATIONIQ_API_KEY

    }
}
package af.amir.weathermvi.data.remote.weather

import af.amir.weathermvi.data.remote.weather.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?current=temperature_2m,relative_humidity_2m,weather_code,pressure_msl,wind_speed_10m&hourly=temperature_2m,weather_code&timezone=auto")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
    ): Response<WeatherDto>

}
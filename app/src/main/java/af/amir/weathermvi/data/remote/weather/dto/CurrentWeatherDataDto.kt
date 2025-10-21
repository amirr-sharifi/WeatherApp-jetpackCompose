package af.amir.weathermvi.data.remote.weather.dto

import com.google.gson.annotations.SerializedName


data class CurrentWeatherDataDto(
    val time : String?,
    @SerializedName("temperature_2m")
    val temperature : Double?,
    @SerializedName( "weather_code")
    val weatherCode : Int?,
    @SerializedName("relative_humidity_2m")
    val humidity : Int?,
    @SerializedName( "wind_speed_10m")
    val windSpeed : Double?,
    @SerializedName( "pressure_msl")
    val pressure : Double?,
)

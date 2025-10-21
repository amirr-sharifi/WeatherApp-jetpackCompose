package af.amir.weathermvi.data.remote.weather.dto

import com.google.gson.annotations.SerializedName


data class WeatherDataDto(
    val time : List<String>,
    @SerializedName("temperature_2m")
    val temperatures : List<Double>,
    @SerializedName( "weather_code")
    val weatherCode : List<Int>,
)

package af.amir.weathermvi.data.remote.weather.dto

import com.google.gson.annotations.SerializedName


data class WeatherDto(
    @SerializedName("hourly")
    val weatherData : WeatherDataDto,
    @SerializedName( "current")
    val currentWeatherData : CurrentWeatherDataDto
)

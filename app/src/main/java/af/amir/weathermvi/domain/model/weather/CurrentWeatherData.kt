package af.amir.weathermvi.domain.model.weather

data class CurrentWeatherData(
    val time : String?,
    val temperature : Double?,
    val weatherTypeCode : Int,
    val humidity : Int?,
    val windSpeed : Double?,
    val pressure : Double?,
)

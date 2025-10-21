package af.amir.weathermvi.domain.model.weather



data class FutureHourlyWeatherData(
    val time : String,
    val temperature : Double,
    val weatherTypeCode : Int,
)

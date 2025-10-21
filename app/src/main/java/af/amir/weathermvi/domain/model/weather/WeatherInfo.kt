package af.amir.weathermvi.domain.model.weather

data class WeatherInfo(
    val currentWeather: CurrentWeatherData?,
    val weatherDataPerDay: Map<Int, List<FutureHourlyWeatherData>>,
    val cityName: String,
)
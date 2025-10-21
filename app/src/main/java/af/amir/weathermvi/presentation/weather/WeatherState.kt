package af.amir.weathermvi.presentation.weather

import af.amir.weathermvi.domain.model.weather.CurrentWeatherData
import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData

data class WeatherState(
    val cityName :String ="",
    val currentWeatherData: CurrentWeatherData? = null,
    val weatherDataPerDay: Map<Int, List<FutureHourlyWeatherData>>? = null,
    val selectedDayIndex: Int = 0,
    val showingBottomSheet  : Boolean = false,
    val futureHourlyWeatherData: List<FutureHourlyWeatherData> = emptyList(),
    val localLoading: Boolean = false,
    val isRefreshing : Boolean = false
)
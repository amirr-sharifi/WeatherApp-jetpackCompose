package af.amir.weathermvi.data.mappers

import af.amir.weathermvi.data.local.weather.WeatherInfoEntity
import af.amir.weathermvi.data.remote.weather.dto.CurrentWeatherDataDto
import af.amir.weathermvi.data.remote.weather.dto.WeatherDataDto
import af.amir.weathermvi.data.remote.weather.dto.WeatherDto
import af.amir.weathermvi.domain.model.weather.CurrentWeatherData
import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData
import af.amir.weathermvi.domain.model.weather.WeatherInfo
import af.amir.weathermvi.presentation.model.AnimatedWeatherType
import af.amir.weathermvi.presentation.model.WeatherType
import android.util.Log
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

private val timeFormatter = DateTimeFormatter.ofPattern("EEEE : dd/MM")

private data class IndexedWeatherData(
    val index: Int,
    val data: FutureHourlyWeatherData,
)


fun WeatherDataDto.toHourlyWeatherDataMap(): Map<Int, List<FutureHourlyWeatherData>> {
    return time.mapIndexed { index, time ->
        IndexedWeatherData(
            index = index,
            data = FutureHourlyWeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME).toLocalTime().format(
                    DateTimeFormatter.ISO_LOCAL_TIME),
                temperature = temperatures[index],
                weatherTypeCode = weatherCode[index],
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}


fun CurrentWeatherDataDto.toCurrentWeatherData(): CurrentWeatherData {

    Log.e("Weather", "toCurrentWeatherData: ${this.time}", )
    return CurrentWeatherData(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME).toLocalTime().format(
            DateTimeFormatter.ISO_LOCAL_TIME),
        temperature = temperature,
        weatherTypeCode = weatherCode ?: 0,
        humidity = humidity,
        windSpeed = windSpeed,
        pressure = pressure
    )

}

fun WeatherDto.toWeatherInfoEntity(cityName: String): WeatherInfoEntity {
    val hourlyWeatherData = weatherData.toHourlyWeatherDataMap()
    val currentWeatherData = currentWeatherData.toCurrentWeatherData()
    return WeatherInfoEntity(
        weatherDataPerDay = hourlyWeatherData,
        currentWeather = currentWeatherData,
        cityName = cityName
    )
}

fun WeatherInfoEntity.toWeatherInfoMap(): WeatherInfo {
    return WeatherInfo(
        currentWeather = currentWeather,
        weatherDataPerDay = weatherDataPerDay,
        cityName = cityName
    )
}


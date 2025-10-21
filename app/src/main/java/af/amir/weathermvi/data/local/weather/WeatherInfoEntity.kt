package af.amir.weathermvi.data.local.weather

import af.amir.weathermvi.domain.model.weather.CurrentWeatherData
import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherInfoEntity(
    val currentWeather: CurrentWeatherData?,
    val weatherDataPerDay: Map<Int, List<FutureHourlyWeatherData>>,
    @PrimaryKey val cityName : String,
)

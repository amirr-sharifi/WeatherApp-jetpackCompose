package af.amir.weathermvi.domain.repository

import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.domain.util.Result
import af.amir.weathermvi.domain.util.WeatherError
import af.amir.weathermvi.domain.model.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherData(location: LocationInfo): Flow<Result<WeatherInfo, WeatherError>>
    fun fetchWeatherDataFromRemote(location: LocationInfo): Flow<Result<WeatherInfo, WeatherError>>
}
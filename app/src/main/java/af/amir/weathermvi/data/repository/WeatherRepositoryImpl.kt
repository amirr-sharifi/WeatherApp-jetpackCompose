package af.amir.weathermvi.data.repository

import af.amir.weathermvi.data.local.weather.WeatherDao
import af.amir.weathermvi.data.mappers.toWeatherInfoEntity
import af.amir.weathermvi.data.mappers.toWeatherInfoMap
import af.amir.weathermvi.data.remote.weather.WeatherApi
import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.domain.repository.WeatherRepository
import af.amir.weathermvi.data.helper.CheckInternetConnection
import af.amir.weathermvi.data.local.weather.WeatherInfoEntity
import af.amir.weathermvi.domain.util.Result
import af.amir.weathermvi.domain.util.WeatherError
import af.amir.weathermvi.domain.model.weather.WeatherInfo
import af.amir.weathermvi.domain.util.Error
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    private val checkInternetConnection: CheckInternetConnection,
) : WeatherRepository {

    override fun getWeatherData(location: LocationInfo): Flow<Result<WeatherInfo, WeatherError>> {
        val cityName = location.cityName ?: return flowOf<Result<WeatherInfo, WeatherError>>(
            Result.Error(
                WeatherError.UnknownError("City Name is Null!")
            )
        )

        return weatherDao.getWeatherInfoByName(cityName)
            .map<WeatherInfoEntity?, Result<WeatherInfo, WeatherError>> { entity ->
                entity?.let {
                    Result.Success(entity.toWeatherInfoMap())
                } ?: Result.Error(WeatherError.Local.No_Data)
            }.onStart {
                emit(Result.Loading())
            }

        /* return channelFlow {
             val localFlow = weatherDao.getWeatherInfoByName(cityName)
             launch {
                 localFlow.onEach {
                     send(Result.Loading(localLoading = true))
                 }.collectLatest {entity ->
                     send(Result.Loading(localLoading = false))

                 }
             }

             launch {
                 send(Result.Loading)
             }
         }*/
        /*<Result<WeatherInfo, WeatherError> > {



            emit(Result.Loading(remoteLoading = true))
            if (checkInternetConnection.invoke()) {
                try {
                    fetchWeatherDataFromRemote(location)
                } catch (e: Exception) {
                    emit(Result.Error(WeatherError.Network.Loading_Data))
                }
            } else {
                emit(Result.Error(WeatherError.Network.Network_Is_Off))
            }
            emit(Result.Loading(remoteLoading = false))


        }*/
    }

    override fun fetchWeatherDataFromRemote(
        location: LocationInfo,
    ): Flow<Result<WeatherInfo, WeatherError>> {
        return flow{
            emit(Result.Loading())
            if (checkInternetConnection.invoke()) {
                try {
                    val weatherResponse = weatherApi.getWeatherData(location.lat, location.long)
                    if (weatherResponse.isSuccessful){
                        weatherResponse.body()?.let {weatherDto ->
                            val weatherInfoEntity = weatherDto.toWeatherInfoEntity(location.cityName!!)
                            weatherDao.updateWeather(weatherInfoEntity)
                            emit(Result.Success(weatherInfoEntity.toWeatherInfoMap()))
                        }?:run{
                            emit(Result.Error(WeatherError.Network.Loading_Data))
                        }
                    }else{
                        emit(Result.Error(WeatherError.UnknownError(weatherResponse.message())))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Result.Error(WeatherError.Network.Loading_Data))
                }
            } else {
                emit(Result.Error(WeatherError.Network.Network_Is_Off))
            }

        }
    }


}
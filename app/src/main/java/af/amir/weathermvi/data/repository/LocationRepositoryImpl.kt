package af.amir.weathermvi.data.repository

import af.amir.weathermvi.data.local.location.LocationDao
import af.amir.weathermvi.data.mappers.toLocationInfo
import af.amir.weathermvi.data.mappers.toLocationInfoEntity
import af.amir.weathermvi.data.remote.locationIQ.LocationIQApi
import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.domain.tracker.LocationTracker
import af.amir.weathermvi.domain.repository.LocationRepository
import af.amir.weathermvi.data.helper.CheckInternetConnection
import af.amir.weathermvi.domain.util.Result
import af.amir.weathermvi.domain.util.WeatherError
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationIQApi,
    private val checkInternetConnection: CheckInternetConnection,
    private val locationDao: LocationDao,
    private val locationTracker: LocationTracker,
) : LocationRepository {


    override fun useDeviceLocation(
    ): Flow<Result<LocationInfo, WeatherError>> {
        return flow {
            locationTracker.getCurrentLocation()?.let {
                emitAll(
                    getReverseCoddingData(it.lat, it.long)
                )
            } ?: run {
                emit(Result.Error(WeatherError.Location.Cant_Reach))
            }

        }
    }

    override fun getLocationFromDb(): LocationInfo? {
        return locationDao.getLocation()?.toLocationInfo()
    }

    override suspend fun getCityListing(query: String): Flow<Result<List<LocationInfo>, WeatherError>> {
        return flow<Result<List<LocationInfo>, WeatherError>> {
            emit(Result.Loading())
            if (query.isBlank() || query == "") {
                emit(Result.Success(data = emptyList()))
                return@flow
            }

            if (!checkInternetConnection.invoke()) {
                emit(Result.Error(WeatherError.Network.Network_Is_Off))
                return@flow
            }

            val list =
                api.getLocationAutoCompleteList(query).toList().map { it.toLocationInfo() }
                emit(Result.Success(data = list))
        }.catch { exception ->
            handleError(exception)
        }
    }

    override suspend fun getReverseCoddingData(
        lat: Double,
        long: Double,
    ): Flow<Result<LocationInfo, WeatherError>> {
        return flow{
            emit(Result.Loading())
            val data = api.getReverseCoddingData(lat, long)
            val newLocation = data.toLocationInfoEntity()
            locationDao.addLocation(newLocation)
            getLocalLocation()

        }.catch { exception ->
            handleError(exception)
        }
    }


    private suspend fun <T> FlowCollector<Result<T, WeatherError>>.handleError(exception: Throwable) {
        when (exception) {
            is SocketTimeoutException -> emit(Result.Error(WeatherError.Network.TimeOut))
            is IOException -> emit(Result.Error(WeatherError.Network.Loading_Data))
            else -> {
                emit(Result.Error(WeatherError.UnknownError(exception.message.toString())))
                Log.e("UnKnownError", "handleError: ${exception.message}")
            }
        }
    }

    private suspend fun FlowCollector<Result<LocationInfo, WeatherError>>.getLocalLocation() {
        locationDao.getLocation()?.let {
            emit(Result.Success(data = it.toLocationInfo()))
        } ?: run {
            emit(Result.Error(WeatherError.UnknownError("Cant get Data !!")))
        }
    }


}
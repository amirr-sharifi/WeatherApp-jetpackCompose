package af.amir.weathermvi.di

import af.amir.weathermvi.data.remote.locationIQ.LocationIQApi
import af.amir.weathermvi.data.remote.weather.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun providesWeatherApi(
    ): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherApi::class.java)

    }

    @Provides
    @Singleton
    fun providesGeocoderApi(
    ): LocationIQApi {
        return Retrofit.Builder()
            .baseUrl("https://us1.locationiq.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LocationIQApi::class.java)

    }




}
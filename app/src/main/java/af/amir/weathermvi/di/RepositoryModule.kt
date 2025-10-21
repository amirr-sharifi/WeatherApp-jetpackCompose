package af.amir.weathermvi.di

import af.amir.weathermvi.data.repository.LocationRepositoryImpl
import af.amir.weathermvi.data.repository.WeatherRepositoryImpl
import af.amir.weathermvi.domain.repository.LocationRepository
import af.amir.weathermvi.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        repositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindFindCityRepository(
        repositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
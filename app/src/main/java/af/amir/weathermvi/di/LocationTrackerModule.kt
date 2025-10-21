package af.amir.weathermvi.di

import af.amir.weathermvi.data.tracker.DefaultLocationTracker
import af.amir.weathermvi.domain.tracker.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationTrackerModule {

    @Binds
    @Singleton
    abstract fun bindsLocationTracker(
        locationTracker: DefaultLocationTracker
    ): LocationTracker

}
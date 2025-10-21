package af.amir.weathermvi.di

import af.amir.weathermvi.data.local.WeatherDatabase
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        app: Application,
    ): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weatherDB"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(
        db: WeatherDatabase,
    ) = db.weatherDao

    @Provides
    @Singleton
    fun provideLocationDao(
        db: WeatherDatabase,
    ) = db.locationDao
}
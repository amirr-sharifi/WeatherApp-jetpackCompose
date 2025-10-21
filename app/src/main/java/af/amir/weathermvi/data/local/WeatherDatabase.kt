package af.amir.weathermvi.data.local

import af.amir.weathermvi.data.local.converter.Converters
import af.amir.weathermvi.data.local.location.LocationDao
import af.amir.weathermvi.data.local.location.LocationInfoEntity
import af.amir.weathermvi.data.local.weather.WeatherDao
import af.amir.weathermvi.data.local.weather.WeatherInfoEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeatherInfoEntity::class,LocationInfoEntity::class], version = 1 )
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao : WeatherDao
    abstract val locationDao : LocationDao
}
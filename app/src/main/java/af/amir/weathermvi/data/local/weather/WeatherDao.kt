package af.amir.weathermvi.data.local.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {


    @Transaction
    suspend fun updateWeather(data: WeatherInfoEntity){
        deleteALl()
        return insert(data)
    }


    @Query("DELETE FROM WeatherInfoEntity")
    suspend fun deleteALl()

    @Insert
    suspend fun insert(data: WeatherInfoEntity)

    @Query("SELECT * FROM WeatherInfoEntity WHERE cityName =:cityName")
     fun getWeatherInfoByName(cityName :String): Flow<WeatherInfoEntity?>

}
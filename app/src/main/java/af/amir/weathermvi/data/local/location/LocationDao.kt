package af.amir.weathermvi.data.local.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LocationDao {

    @Transaction
    suspend fun addLocation(locationEntity: LocationInfoEntity){
        deleteLocation()
        insert(locationEntity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationEntity: LocationInfoEntity) : Long

    @Query("DELETE FROM LocationInfoEntity")
    suspend fun deleteLocation()

    @Query("SELECT * FROM LocationInfoEntity")
     fun getLocation() : LocationInfoEntity?

    /*@Query("SELECT * FROM LocationInfoEntity WHERE lat =:lat AND lon=:lon")
    suspend fun getLocationByInfo(lat: Double, lon:Double) : LocationInfoEntity?*/



}
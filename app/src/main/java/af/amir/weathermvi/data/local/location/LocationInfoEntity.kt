package af.amir.weathermvi.data.local.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationInfoEntity(
    val lat: Double,
    val lon: Double,
    val cityName : String?,
    @PrimaryKey val id : Long? = null
)

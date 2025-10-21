package af.amir.weathermvi.data.mappers

import af.amir.weathermvi.data.local.location.LocationInfoEntity
import af.amir.weathermvi.data.remote.locationIQ.dto.LocationAutoCompleteDto
import af.amir.weathermvi.data.remote.locationIQ.dto.ReverseCodingDto
import af.amir.weathermvi.domain.model.location.LocationInfo


fun LocationInfoEntity.toLocationInfo(): LocationInfo {
    return LocationInfo(
        lat = lat,
        long = lon,
        cityName = cityName?:""
    )
}

fun LocationAutoCompleteDto.toLocationInfo(): LocationInfo {
    return LocationInfo(
        lat = lat.toDouble(),
        long = lon.toDouble(),
        cityName = display_name
    )
}

fun ReverseCodingDto.toLocationInfoEntity(): LocationInfoEntity {
    val city = address.neighbourhood?:address.town?:address.city?:"Unknown"

    return LocationInfoEntity(
        lat = lat.toDouble(),
        lon = lon.toDouble(),
        cityName = city
    )
}
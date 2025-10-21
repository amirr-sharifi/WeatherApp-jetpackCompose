package af.amir.weathermvi.data.remote.locationIQ.dto

data class ReverseCodingDto(
    val address: ReverseCoddingAddressDto,
    val lat: String,
    val lon: String,
)
package af.amir.weathermvi.domain.util

interface WeatherError : Error {
    sealed interface Location : WeatherError {
        object Cant_Reach : Location
    }

    sealed interface Local : Error{
        object No_Data : WeatherError
    }

    sealed interface Network : WeatherError {
        object TimeOut : Network
        object Loading_Data : Network
        object Network_Is_Off : Network
    }

    class UnknownError(val message: String) : WeatherError
}
package af.amir.weathermvi.presentation.weather

sealed class WeatherUiEvents {
    class ShowMessage(val message: String) : WeatherUiEvents()
    class EditLocation(val cityName : String ): WeatherUiEvents()
}
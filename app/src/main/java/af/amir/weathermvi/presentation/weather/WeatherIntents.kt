package af.amir.weathermvi.presentation.weather

sealed class WeatherIntents {
    data object Refresh : WeatherIntents()
    class ChangeSelectedIndex(val index :Int):WeatherIntents()
    data object DismissBottomSheet : WeatherIntents()
    data object EditCurrentLocation : WeatherIntents()
}
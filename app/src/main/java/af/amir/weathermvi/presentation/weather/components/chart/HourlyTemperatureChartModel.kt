package af.amir.weathermvi.presentation.weather.components.chart

import org.threeten.bp.LocalTime

data class HourlyTemperatureChartModel(
    val hour : LocalTime,
    val temp :Float
)

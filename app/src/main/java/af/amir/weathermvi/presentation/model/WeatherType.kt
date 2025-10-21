package af.amir.weathermvi.presentation.model

import androidx.annotation.DrawableRes
import af.amir.weathermvi.R
import androidx.annotation.StringRes


sealed class WeatherType(
    @StringRes val weatherDesc: Int,
    @DrawableRes val dayIcon: Int,
    @DrawableRes val nightIcon: Int,
) {
    object ClearSky : WeatherType(
        weatherDesc = R.string.clear_sky,
        dayIcon = R.drawable.day_sunny,
        nightIcon = R.drawable.night_clear
    )

    object MainlyClear : WeatherType(
        weatherDesc = R.string.mainly_clear,
        dayIcon = R.drawable.day_sunny,
        nightIcon = R.drawable.night_clear
    )

    object PartlyCloudy : WeatherType(
        weatherDesc = R.string.partly_cloudy,
        dayIcon = R.drawable.day_overcast,
        nightIcon = R.drawable.night_partly_cloudy,
    )

    object Overcast : WeatherType(
        weatherDesc = R.string.overcast,
        dayIcon = R.drawable.day_cloudy,
        nightIcon = R.drawable.night_cloudy,

    )

    object Foggy : WeatherType(
        weatherDesc = R.string.foggy,
        dayIcon = R.drawable.day_fog,
        nightIcon = R.drawable.night_fog,
    )

    object DepositingRimeFog : WeatherType(
        weatherDesc = R.string.depositing_rime_fog,
        dayIcon = R.drawable.day_fog,
        nightIcon = R.drawable.night_fog,
    )

    object LightDrizzle : WeatherType(
        weatherDesc = R.string.light_drizzle,
        dayIcon = R.drawable.day_rain,
        nightIcon = R.drawable.night_rain,
    )

    object ModerateDrizzle : WeatherType(
        weatherDesc = R.string.moderate_drizzle,
        dayIcon = R.drawable.day_rain,
        nightIcon = R.drawable.night_rain,
    )

    object DenseDrizzle : WeatherType(
        weatherDesc = R.string.dense_drizzle,
        dayIcon = R.drawable.day_rain,
        nightIcon = R.drawable.night_rain,
    )

    object LightFreezingDrizzle : WeatherType(
        weatherDesc = R.string.slight_freezing_drizzle,
        dayIcon = R.drawable.day_rain_freezing,
        nightIcon = R.drawable.night_rain_freezing
    )

    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = R.string.dense_freezing_drizzle,
        dayIcon = R.drawable.day_rain_freezing,
        nightIcon = R.drawable.night_rain_freezing
    )

    object SlightRain : WeatherType(
        weatherDesc = R.string.slight_rain,
        dayIcon = R.drawable.day_rain,
        nightIcon = R.drawable.night_rain,
    )

    object ModerateRain : WeatherType(
        weatherDesc = R.string.rainy,
        dayIcon = R.drawable.day_rain,
        nightIcon = R.drawable.night_rain,
    )

    object HeavyRain : WeatherType(
        weatherDesc = R.string.heavy_rain,
        dayIcon = R.drawable.day_rain,
        nightIcon = R.drawable.night_rain,
    )

    object HeavyFreezingRain : WeatherType(
        weatherDesc = R.string.heavy_freezing_rain,
        dayIcon = R.drawable.day_rain_freezing,
        nightIcon = R.drawable.night_rain_freezing
    )

    object SlightSnowFall : WeatherType(
        weatherDesc = R.string.slight_snow_fall,
        dayIcon = R.drawable.day_snow,
        nightIcon = R.drawable.night_snow
    )

    object ModerateSnowFall : WeatherType(
        weatherDesc = R.string.moderate_snow_fall,
        dayIcon = R.drawable.day_snow,
        nightIcon = R.drawable.night_snow
    )

    object HeavySnowFall : WeatherType(
        weatherDesc =R.string.heavy_snow_fall,
        dayIcon = R.drawable.day_snow,
        nightIcon = R.drawable.night_snow
    )

    object SnowGrains : WeatherType(
        weatherDesc = R.string.snow_grains,
        dayIcon = R.drawable.day_snow,
        nightIcon = R.drawable.night_snow
    )

    object SlightRainShowers : WeatherType(
        weatherDesc = R.string.slight_rain_showers,
        dayIcon = R.drawable.day_showers,
        nightIcon = R.drawable.night_showers
    )

    object ModerateRainShowers : WeatherType(
        weatherDesc = R.string.moderate_rain_showers,
        dayIcon = R.drawable.day_showers,
        nightIcon = R.drawable.night_showers
    )

    object ViolentRainShowers : WeatherType(
        weatherDesc = R.string.violent_rain_showers,
        dayIcon = R.drawable.day_showers,
        nightIcon = R.drawable.night_showers
    )

    object SlightSnowShowers : WeatherType(
        weatherDesc = R.string.light_snow_showers,
        dayIcon = R.drawable.day_snow,
        nightIcon = R.drawable.night_snow
    )

    object HeavySnowShowers : WeatherType(
        weatherDesc = R.string.heavy_snow_showers,
        dayIcon = R.drawable.day_snow,
        nightIcon = R.drawable.night_snow
    )

    object ModerateThunderstorm : WeatherType(
        weatherDesc = R.string.moderate_thunderstorm,
        dayIcon = R.drawable.day_thunderstorm,
        nightIcon = R.drawable.night_thunderstorm
    )

    object SlightHailThunderstorm : WeatherType(
        weatherDesc = R.string.thunderstorm_with_slight_hail,
        dayIcon = R.drawable.day_thunderstorm,
        nightIcon = R.drawable.night_thunderstorm
    )

    object HeavyHailThunderstorm : WeatherType(
        weatherDesc = R.string.thunderstorm_with_heavy_hail,
        dayIcon = R.drawable.day_thunderstorm,
        nightIcon = R.drawable.night_thunderstorm
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }

        fun toWMO(type: WeatherType): Int {
            return when (type) {
                ClearSky -> 0
                MainlyClear -> 1
                PartlyCloudy -> 2
                Overcast -> 3
                Foggy -> 45
                DepositingRimeFog -> 48
                LightDrizzle -> 51
                ModerateDrizzle -> 53
                DenseDrizzle -> 55
                LightFreezingDrizzle -> 56
                DenseFreezingDrizzle -> 57
                SlightRain -> 61
                ModerateRain -> 63
                HeavyRain -> 65
                LightFreezingDrizzle -> 66
                HeavyFreezingRain -> 67
                SlightSnowFall -> 71
                ModerateSnowFall -> 73
                HeavySnowFall -> 75
                SnowGrains -> 77
                SlightRainShowers -> 80
                ModerateRainShowers -> 81
                ViolentRainShowers -> 82
                SlightSnowShowers -> 85
                HeavySnowShowers -> 86
                ModerateThunderstorm -> 95
                SlightHailThunderstorm -> 96
                HeavyHailThunderstorm -> 99
            }
        }
    }
}
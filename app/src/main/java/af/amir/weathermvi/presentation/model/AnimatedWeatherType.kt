package af.amir.weathermvi.presentation.model

import af.amir.weathermvi.R
import androidx.annotation.RawRes
import androidx.annotation.StringRes

sealed class AnimatedWeatherType(
    @StringRes open val weatherDesc: Int,
    @RawRes open val dayIcon: Int,
    @RawRes open val nightIcon: Int,
) {
    object ClearSky : AnimatedWeatherType(
        weatherDesc = R.string.clear_sky,
        dayIcon = R.raw.clear_day,
        nightIcon = R.raw.clear_night,
    )

    object MainlyClear : AnimatedWeatherType(
        weatherDesc = R.string.mainly_clear,
        dayIcon = R.raw.clear_day,
        nightIcon = R.raw.clear_night,
    )

    object PartlyCloudy : AnimatedWeatherType(
        weatherDesc = R.string.partly_cloudy,
        dayIcon = R.raw.partly_cloudy_day,
        nightIcon = R.raw.partly_cloudy_night,
    )


    object Overcast : AnimatedWeatherType(
        weatherDesc = R.string.overcast,
        dayIcon = R.raw.overcast_day,
        nightIcon = R.raw.overcast_night,
    )

    object Foggy : AnimatedWeatherType(
        weatherDesc = R.string.foggy,
        dayIcon = R.raw.fog,
        nightIcon = R.raw.fog,
    )

    object DepositingRimeFog : AnimatedWeatherType(
        weatherDesc = R.string.depositing_rime_fog,
        dayIcon = R.raw.fog_day,
        nightIcon = R.raw.fog_night,
    )

    object LightDrizzle : AnimatedWeatherType(
        weatherDesc = R.string.light_drizzle,
        dayIcon = R.raw.light_day_drizzle,
        nightIcon = R.raw.light_night_drizzle,
    )

    object ModerateDrizzle : AnimatedWeatherType(
        weatherDesc = R.string.moderate_drizzle,
        dayIcon = R.raw.moderate_day_drizzle,
        nightIcon = R.raw.moderate_night_drizzle,
    )

    object DenseDrizzle : AnimatedWeatherType(
        weatherDesc = R.string.dense_drizzle,
        dayIcon = R.raw.drizzle,
        nightIcon = R.raw.drizzle,
    )

    object LightFreezingDrizzle : AnimatedWeatherType(
        weatherDesc = R.string.slight_freezing_drizzle,
        dayIcon = R.raw.light_day_freezing_drizzle,
        nightIcon = R.raw.light_night_freezing_drizzle,
    )

    object DenseFreezingDrizzle : AnimatedWeatherType(
        weatherDesc = R.string.dense_freezing_drizzle,
        dayIcon = R.raw.freezing_drizzle,
        nightIcon = R.raw.freezing_drizzle,
    )

    object SlightRain : AnimatedWeatherType(
        weatherDesc = R.string.slight_rain,
        dayIcon = R.raw.slight_day_rain,
        nightIcon = R.raw.slight_night_rain,
    )

    object ModerateRain : AnimatedWeatherType(
        weatherDesc = R.string.rainy,
        dayIcon = R.raw.moderate_day_rain,
        nightIcon = R.raw.moderate_night_rain,
    )

    object HeavyRain : AnimatedWeatherType(
        weatherDesc = R.string.heavy_rain,
        dayIcon = R.raw.heavy_day_rain,
        nightIcon = R.raw.heavy_night_rain,
    )

    object HeavyFreezingRain : AnimatedWeatherType(
        weatherDesc = R.string.heavy_freezing_rain,
        dayIcon = R.raw.heavy_day_freezing_rain,
        nightIcon = R.raw.heavy_night_freezing_rain,
    )

    object SlightSnowFall : AnimatedWeatherType(
        weatherDesc = R.string.slight_snow_fall,
        dayIcon = R.raw.slight_day_snow,
        nightIcon = R.raw.slight_night_snow,
    )

    object ModerateSnowFall : AnimatedWeatherType(
        weatherDesc = R.string.moderate_snow_fall,
        dayIcon = R.raw.moderate_day_snow,
        nightIcon = R.raw.moderate_night_snow,
    )

    object HeavySnowFall : AnimatedWeatherType(
        weatherDesc = R.string.heavy_snow_fall,
        dayIcon = R.raw.heavy_day_snow,
        nightIcon = R.raw.heavy_night_snow,
    )

    object SnowGrains : AnimatedWeatherType(
        weatherDesc = R.string.snow_grains,
        dayIcon = R.raw.snow,
        nightIcon = R.raw.snow,
    )

    object SlightRainShowers : AnimatedWeatherType(
        weatherDesc = R.string.slight_rain_showers,
        dayIcon = R.raw.slight_day_rain,
        nightIcon = R.raw.slight_night_rain
    )

    object ModerateRainShowers : AnimatedWeatherType(
        weatherDesc = R.string.moderate_rain_showers,
        dayIcon = R.raw.moderate_day_rain,
        nightIcon = R.raw.moderate_night_rain,
    )

    object ViolentRainShowers : AnimatedWeatherType(
        weatherDesc = R.string.violent_rain_showers,
        dayIcon = R.raw.heavy_day_rain,
        nightIcon = R.raw.heavy_night_rain,
    )

    object SlightSnowShowers : AnimatedWeatherType(
        weatherDesc = R.string.light_snow_showers,
        dayIcon = R.raw.slight_day_snow,
        nightIcon = R.raw.slight_night_snow,
    )

    object HeavySnowShowers : AnimatedWeatherType(
        weatherDesc = R.string.heavy_snow_showers,
        dayIcon = R.raw.heavy_day_snow,
        nightIcon = R.raw.heavy_night_snow,
    )

    object ModerateThunderstorm : AnimatedWeatherType(
        weatherDesc = R.string.moderate_thunderstorm,
        dayIcon = R.raw.moderate_day_thunderstorm,
        nightIcon = R.raw.moderate_night_thunderstorm,
    )

    object SlightHailThunderstorm : AnimatedWeatherType(
        weatherDesc = R.string.thunderstorm_with_slight_hail,
        dayIcon = R.raw.slight_day_hail,
        nightIcon = R.raw.slight_night_hail,
    )

    object HeavyHailThunderstorm : AnimatedWeatherType(
        weatherDesc = R.string.thunderstorm_with_heavy_hail,
        dayIcon = R.raw.heavy_day_hail,
        nightIcon = R.raw.heavy_night_hail,
    )


    companion object {
        fun fromWMO(
            code: Int,
        ): AnimatedWeatherType {
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

        fun toWMO(type: AnimatedWeatherType): Int {
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
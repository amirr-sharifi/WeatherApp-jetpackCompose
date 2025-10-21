package af.amir.weathermvi.data.local.converter

import af.amir.weathermvi.domain.model.weather.CurrentWeatherData
import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData
import af.amir.weathermvi.presentation.model.AnimatedWeatherType
import af.amir.weathermvi.presentation.model.WeatherType
import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Converters {

    val gson =
        GsonBuilder()
            .registerTypeAdapter(
                AnimatedWeatherType::class.java,
                AnimatedWeatherTypeAdapter()
            )
            .registerTypeAdapter(
                WeatherType::class.java,
                WeatherTypeAdapter()
            )
            .create()

    @TypeConverter
    fun fromCurrentData(data: CurrentWeatherData?): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun toCurrentData(value: String?): CurrentWeatherData? {
        val type = object : TypeToken<CurrentWeatherData>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromMap(map: Map<Int, List<FutureHourlyWeatherData>>?): String? {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toMap(value: String?): Map<Int, List<FutureHourlyWeatherData>>? {
        val type = object : TypeToken<Map<Int, List<FutureHourlyWeatherData>>>() {}.type
        return gson.fromJson(value, type)
    }

}
package af.amir.weathermvi.data.local.converter

import af.amir.weathermvi.presentation.model.WeatherType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class WeatherTypeAdapter : JsonDeserializer<WeatherType>,
    JsonSerializer<WeatherType> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): WeatherType {
        if (json == null || json.isJsonNull) {
            return WeatherType.ClearSky
        }

        val typeCode = json.asIntOrNull() ?: return WeatherType.ClearSky
        return WeatherType.fromWMO(typeCode)
    }

    override fun serialize(
        src: WeatherType?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return JsonPrimitive(WeatherType.toWMO(src ?: WeatherType.ClearSky))
    }
}

// Extension function for safe conversion
fun JsonElement.asIntOrNull(): Int? {
    return try {
        this.asInt
    } catch (e: Exception) {
        null
    }
}

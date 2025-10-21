package af.amir.weathermvi.data.local.converter

import af.amir.weathermvi.presentation.model.AnimatedWeatherType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class AnimatedWeatherTypeAdapter : JsonDeserializer<AnimatedWeatherType>,
    JsonSerializer<AnimatedWeatherType> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): AnimatedWeatherType {
        if (json == null || json.isJsonNull) {
            return AnimatedWeatherType.ClearSky
        }

        val typeCode = json.asIntOrNull() ?: return AnimatedWeatherType.ClearSky
        return AnimatedWeatherType.fromWMO(typeCode)
    }

    override fun serialize(
        src: AnimatedWeatherType?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return JsonPrimitive(AnimatedWeatherType.toWMO(src ?: AnimatedWeatherType.ClearSky))
    }
}



package af.amir.weathermvi.presentation.weather.components

import af.amir.weathermvi.presentation.model.WeatherType
import af.amir.weathermvi.presentation.theme.RoundedShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.threeten.bp.LocalTime


@Composable
fun HourlyDetailCard(
    time: LocalTime,
    weatherType: WeatherType,
    cardBackgroundColor: Color,
    contentColor: Color,
    temperature: Int,
) {

    val isDay = time.hour in 6..18
    val icon = if (isDay) weatherType.dayIcon else weatherType.nightIcon

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(cardBackgroundColor, shape = RoundedShape)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {

        Text(
            text = "$time",
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            color = contentColor
        )
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            tint = contentColor,
            modifier = Modifier.size(32.dp),
            contentDescription = stringResource(weatherType.weatherDesc)
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "$temperature°C",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                color = contentColor,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        )
    }

    Spacer(Modifier.width(16.dp))

}
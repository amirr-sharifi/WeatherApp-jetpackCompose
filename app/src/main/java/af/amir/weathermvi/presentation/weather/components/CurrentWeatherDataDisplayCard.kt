package af.amir.weathermvi.presentation.weather.components

import af.amir.weathermvi.R
import af.amir.weathermvi.presentation.theme.RoundedShape
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CurrentWeatherDataDisplayCard(
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color,
    valueFontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    labelFontSize: TextUnit = MaterialTheme.typography.labelSmall.fontSize,
    windSpeed: Int = 0,
    windSpeedUnit: String,
    @DrawableRes
    windSpeedIcon: Int,
    humidity: Int = 0,
    humidityUnit: String,
    @DrawableRes
    humidityIcon: Int,
    pressure: Int = 0,
    pressureUnit: String,
    @DrawableRes
    pressureIcon: Int,
    contentColor: Color,
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(cardBackgroundColor, shape = RoundedShape)
            .padding(vertical = 8.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DataDisplay(
            value = windSpeed,
            unit = windSpeedUnit,
            icon = windSpeedIcon,
            label = stringResource(R.string.wind),
            textColor = contentColor,
            valueFontSize = valueFontSize,
            labelFontSize = labelFontSize
        )

        DataDisplay(
            value = humidity,
            unit = humidityUnit,
            icon = humidityIcon,
            label = stringResource(R.string.humidity),
            textColor = contentColor,
            valueFontSize = valueFontSize,
            labelFontSize = labelFontSize
        )

        DataDisplay(
            value = pressure,
            unit = pressureUnit,
            icon = pressureIcon,
            label = stringResource(R.string.pressure),
            textColor = contentColor,
            valueFontSize = valueFontSize,
            labelFontSize = labelFontSize
        )
    }

}

@Composable
private fun DataDisplay(
    value: Int,
    unit: String,
    @DrawableRes icon: Int,
    label: String,
    textColor: Color,
    valueFontSize: TextUnit,
    labelFontSize: TextUnit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            imageVector = ImageVector.vectorResource(icon),
            "",
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "$value$unit",
            fontSize = valueFontSize,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
        Text(
            text = label,
            fontSize = labelFontSize,
            fontWeight = FontWeight.Normal,
            color = textColor
        )


    }
}


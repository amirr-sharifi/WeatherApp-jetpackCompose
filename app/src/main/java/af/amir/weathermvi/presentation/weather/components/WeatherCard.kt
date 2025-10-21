package af.amir.weathermvi.presentation.weather.components

import af.amir.weathermvi.presentation.model.AnimatedWeatherType
import af.amir.weathermvi.presentation.theme.RoundedShape
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.threeten.bp.LocalTime

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color,
    cityName: String,
    weatherTypeLabelFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    temperatureFontSize: TextUnit = 46.sp,
    cityNameFontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    temperature: Int = 0,
    weatherType: AnimatedWeatherType,
    onEditLocationClick: () -> Unit,
    textColor: Color,
) {

    val isDay =
        LocalTime.now().hour in 6..18
    val icon = if (isDay) weatherType.dayIcon else weatherType.nightIcon
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(icon))
    val progress = animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(cardBackgroundColor, shape = RoundedShape)
            .padding(horizontal = 32.dp)
            .padding(top = 16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = cityName,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                fontSize = cityNameFontSize,
                textAlign = TextAlign.Start,
                maxLines = 1, // جلوگیری از رفتن به خط بعدی
                modifier = Modifier.weight(0.8f)
                    .basicMarquee(),
            )
            Spacer(Modifier.width(16.dp))

            IconButton(
                modifier = Modifier.weight(0.2f),
                onClick = { onEditLocationClick() },
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    "",
                    tint = textColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Column(Modifier.weight(1f)) {

                Text(
                    text = stringResource(weatherType.weatherDesc),
                    fontSize = weatherTypeLabelFontSize,
                    color = textColor
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$temperature°C",
                    fontSize = temperatureFontSize,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }




            LottieAnimation(
                composition = composition.value,
                progress = progress.value,
                Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )

        }
    }


}

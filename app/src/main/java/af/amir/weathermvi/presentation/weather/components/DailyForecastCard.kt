package af.amir.weathermvi.presentation.weather.components

import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData
import af.amir.weathermvi.presentation.model.WeatherType
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.threeten.bp.LocalTime
import kotlin.math.roundToInt


@Composable
fun DailyForecastCard(
    modifier: Modifier = Modifier,
    titleList: List<String>,
    cardBackgroundColor : Color,
    contentColor: Color,
    hourlyDetailList: List<FutureHourlyWeatherData>,
    selectedIndex : Int,
    onTitleClick: (index: Int) -> Unit,
) {



    Column(modifier = modifier.fillMaxWidth()) {
        LazyRow(Modifier.fillMaxWidth()) {
            itemsIndexed(titleList) { index, title ->
                val fontWight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Light
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .clip(
                            CircleShape
                        )
                        .clickable {
                            onTitleClick(index)
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = title,
                        style = TextStyle(fontWeight = fontWight),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    AnimatedVisibility(
                        selectedIndex == index,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Box(
                            Modifier
                                .size(6.dp)
                                .clip(CircleShape).background(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(hourlyDetailList) {
                HourlyDetailCard(
                    time = LocalTime.parse(it.time),
                    contentColor = contentColor,
                    cardBackgroundColor = cardBackgroundColor,
                    weatherType = WeatherType.fromWMO(it.weatherTypeCode),
                    temperature = it.temperature.roundToInt()
                )
            }
        }
    }
}


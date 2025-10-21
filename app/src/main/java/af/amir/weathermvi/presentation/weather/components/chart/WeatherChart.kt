package af.amir.weathermvi.presentation.weather.components.chart

import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.pow


@Composable
fun WeatherChart(
    modifier: Modifier = Modifier,
    data: List<FutureHourlyWeatherData>,
    graphStrokeWidth: Dp,
    graphStrokeColor: Color,
    onGraphColor: Color,
) {


    val tempList = data.map { it.toHourlyTemperatureChartModel() }


    val animatedTemps = tempList.map { point ->
        val animatedTemp by animateFloatAsState(
            targetValue = point.temp,
            animationSpec = tween(durationMillis = 500),
            label = "TemperatureAnimation"
        )
        point.copy(temp = animatedTemp)
    }

    val transparentGraphColor by remember { mutableStateOf(graphStrokeColor.copy(alpha = (0.3f))) }
    val upperValue = tempList.maxOfOrNull { it.temp } ?: 0f
    val lowerValue = tempList.minOfOrNull { it.temp } ?: 0f


    var isVisibleDropDownMenu by remember(tempList) { mutableStateOf(false) }
    var selectedTemp by remember(tempList) { mutableStateOf<HourlyTemperatureChartModel?>(null) }
    var selectedPoint by remember(tempList) { mutableStateOf<Pair<Float, Float>?>(null) }

    LaunchedEffect(tempList) {
        isVisibleDropDownMenu = false
        selectedPoint = null
        selectedTemp = null

    }


    val chartHeight = 150f
    val range = (upperValue - lowerValue).coerceAtLeast(1f)

    BoxWithConstraints(modifier = modifier
        .pointerInput(tempList) {
            detectTapGestures { offset ->
                val closest = animatedTemps.minByOrNull { (hour, temp) ->
                    val x = hour.hour * size.width / 23f
                    val y = chartHeight - ((temp - lowerValue) * chartHeight / range)
                    ((offset.x - x).pow(2) + (offset.y - y).pow(2)).pow(0.5f)
                }
                closest?.let { (hour, temp) ->
                    val x = hour.hour * size.width / 23f
                    val y = chartHeight - ((temp - lowerValue) * chartHeight / range)
                    selectedPoint = (x to y)
                    selectedTemp = closest
                    isVisibleDropDownMenu = true
                }


            }
        }) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val height = size.height
            val width = size.width
            var prevX = 0f
            var prevY = 0f

            val strokePath = Path().apply {
                animatedTemps.forEachIndexed { index, (hour, temp) ->
                    val x = hour.hour * width / 23
                    val y = chartHeight - ((temp - lowerValue) * chartHeight / range)

                    if (index == 0) {
                        moveTo(x, y)
                    } else {
                        val controlX = (prevX + x) / 2
                        cubicTo(controlX, prevY, controlX, y, x, y)
                    }

                    prevX = x
                    prevY = y
                }
            }

            val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath()
                .apply {
                    lineTo(prevX, height)
                    lineTo(0f, height)
                    close()
                }

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        transparentGraphColor,
                        Color.Transparent
                    ), endY = height
                ),
            )


            drawPath(
                path = strokePath,
                color = graphStrokeColor,
                style = Stroke(
                    width = graphStrokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )

            selectedPoint?.let { (x, y) ->
                drawCircle(center = Offset(x, y), color = graphStrokeColor, radius = 12f)
                drawCircle(center = Offset(x, y), color = onGraphColor, radius = 9f)
                drawCircle(center = Offset(x, y), color = graphStrokeColor, radius = 6f)
            }

        }
        val screenWidth = constraints.maxWidth
        val screenHeight = constraints.maxHeight

        val density = LocalDensity.current

        selectedPoint?.let { (x, y) ->
            val dpX = with(density) { x.toDp() }
            val dpY = with(density) { y.toDp() }
            DropdownMenu(
                expanded = isVisibleDropDownMenu,
                onDismissRequest = { isVisibleDropDownMenu = false },
                offset = DpOffset(dpX, dpY)
            ) {
                selectedTemp?.let { hourlyTemp ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                    Text(
                        text = "${hourlyTemp.hour}",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                    )
                    Text(
                        text = "${hourlyTemp.temp}°",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                }}
            }
        }

    }


}




private fun FutureHourlyWeatherData.toHourlyTemperatureChartModel(): HourlyTemperatureChartModel {
    return HourlyTemperatureChartModel(LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME ), temperature.toFloat())
}
package af.amir.weathermvi.presentation.weather

import af.amir.weathermvi.R
import af.amir.weathermvi.domain.model.weather.FutureHourlyWeatherData
import af.amir.weathermvi.presentation.model.AnimatedWeatherType
import af.amir.weathermvi.presentation.model.WeatherType
import af.amir.weathermvi.presentation.weather.components.DailyForecastCard
import af.amir.weathermvi.presentation.weather.components.HourlyDetailCard
import af.amir.weathermvi.presentation.weather.components.WeatherCard
import af.amir.weathermvi.presentation.weather.components.chart.WeatherChart
import af.amir.weathermvi.presentation.weather.components.CurrentWeatherDataDisplayCard
import af.amir.weathermvi.presentation.weather.components.ShimmerLoadingView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.roundToInt


@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    onEditCurrentLocation: (cityName: String) -> Unit,
) {
    val viewModel: WeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is WeatherUiEvents.ShowMessage -> {
                    scope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(
                            event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                is WeatherUiEvents.EditLocation -> onEditCurrentLocation(event.cityName)
            }
        }
    }

    Scaffold(
        modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        if (state.localLoading)
            ShimmerLoadingView(modifier = Modifier.padding(paddingValues),)
        else
            state.currentWeatherData?.let {
                WeatherScreenContent(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    onIntents = viewModel::proceedIntents,
                    onEditCurrentLocation = {
                        viewModel.proceedIntents(WeatherIntents.EditCurrentLocation)
                    }
                )
            }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreenContent(
    modifier: Modifier = Modifier,
    state: WeatherState,
    onIntents: (intent: WeatherIntents) -> Unit,
    onEditCurrentLocation: () -> Unit,
) {

    val cardBackgroundColor = MaterialTheme.colorScheme.surfaceContainer
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant

    val paddingModifier = Modifier.padding(horizontal = 16.dp)



    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onIntents(WeatherIntents.Refresh)
        }
    ) {


        //Root View
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {


            //>>>>>>>Weather Info
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                //Weather card
                WeatherCard(
                    modifier = paddingModifier,
                    cardBackgroundColor = cardBackgroundColor,
                    weatherType = AnimatedWeatherType.fromWMO(state.currentWeatherData!!.weatherTypeCode),
                    temperature = state.currentWeatherData.temperature?.roundToInt() ?: 0,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cityName = state.cityName,
                    onEditLocationClick = {
                        onEditCurrentLocation()
                    }
                )

                Spacer(Modifier.height(16.dp))

                CurrentWeatherDataDisplayCard(
                    modifier = paddingModifier,
                    cardBackgroundColor = cardBackgroundColor,
                    windSpeed = state.currentWeatherData.windSpeed?.roundToInt() ?: 0,
                    windSpeedUnit = "km/h",
                    windSpeedIcon = R.drawable.ic_wind,
                    humidity = state.currentWeatherData.humidity ?: 0,
                    humidityUnit = "%",
                    humidityIcon = R.drawable.ic_drop,
                    pressure = state.currentWeatherData.pressure?.roundToInt() ?: 0,
                    pressureUnit = "pp",
                    pressureIcon = R.drawable.ic_pressure,
                    contentColor = contentColor
                )

                Spacer(Modifier.height(16.dp))


                val dailyForecastCardTitles = listOf(
                    stringResource(R.string.today),
                    stringResource(R.string.tomorrow), stringResource(R.string.next_5_days)
                )
                DailyForecastCard(
                    modifier = paddingModifier,
                    titleList = dailyForecastCardTitles,
                    cardBackgroundColor = cardBackgroundColor,
                    contentColor = contentColor,
                    hourlyDetailList = state.futureHourlyWeatherData,
                    selectedIndex = state.selectedDayIndex
                ) {
                    onIntents(WeatherIntents.ChangeSelectedIndex(it))
                }
            }

            //>>>>>>>Chart
            WeatherChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .padding(top = 16.dp),
                data = state.futureHourlyWeatherData,
                graphStrokeColor = contentColor,
                graphStrokeWidth = 2.dp,
                onGraphColor = Color.White
            )
        }

        if (state.showingBottomSheet) {
            ModalBottomSheet(onDismissRequest = {
                onIntents(WeatherIntents.DismissBottomSheet)
            }) {
                BottomSheetContent(
                    weatherDataPerDay = state.weatherDataPerDay!!,
                    contentColor = contentColor,
                    cardBackgroundColor = cardBackgroundColor
                )
            }
        }

    }

}

@Composable
fun BottomSheetContent(
    weatherDataPerDay: Map<Int, List<FutureHourlyWeatherData>>,
    contentColor: Color,
    cardBackgroundColor: Color,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        weatherDataPerDay.values.drop(2).forEach { list ->
            val currentDate = list.first().time
            item(key = list.hashCode()) {
                Text(
                    currentDate,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
            item {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(list) {
                        HourlyDetailCard(
                            time = LocalTime.parse(it.time, DateTimeFormatter.ISO_LOCAL_TIME),
                            contentColor = contentColor,
                            cardBackgroundColor = cardBackgroundColor,
                            weatherType = WeatherType.fromWMO(it.weatherTypeCode),
                            temperature = it.temperature.roundToInt()
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))
            }
        }

    }
}

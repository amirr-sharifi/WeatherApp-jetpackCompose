package af.amir.weathermvi.presentation.cityFinder

import af.amir.weathermvi.R
import af.amir.weathermvi.domain.model.location.LocationInfo
import af.amir.weathermvi.presentation.theme.RoundedShape
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityFinderScreen(
    currentCityName: String?,
    onGetLocationPermission: () -> Unit,
    onTurnOnGps: () -> Unit,
    onNavigateToWeatherScreen: () -> Unit,
    onNavigateBack: () -> Unit,
) {


    val viewModel: CityFinderViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.uiEvent.collect {
            when (it) {
                CityFinderUIEvent.OnEnableGps -> onTurnOnGps()
                CityFinderUIEvent.OnGetLocationPermission -> onGetLocationPermission()
                CityFinderUIEvent.OnNavigateToWeatherScreen -> onNavigateToWeatherScreen()
                is CityFinderUIEvent.ShowMessage -> scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(it.message)
                }
            }
        }
    }


    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {

            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.use_my_location)) },
                icon = { Icon(Icons.Outlined.LocationOn, null) },
                onClick = { viewModel.proceedIntent(CityFinderIntents.RequestLocation(context)) })


        },
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.choose_location))
            }, navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Rounded.ArrowBack, null)
                }
            })
        },

        ) { paddingValues ->



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SearchBox(
                    value = state.searchQuery,
                    placeHolder = if (!currentCityName.isNullOrEmpty()) currentCityName else stringResource(
                        R.string.search
                    ),
                    onValueChange = {
                        viewModel.proceedIntent(CityFinderIntents.OnSearchQueryChange(it))
                    })


                Spacer(Modifier.height(32.dp))

                if (state.isLoading) {
                    LoadingProgressBar()
                } else {
                    if (state.data.isNotEmpty()) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                        ) {
                            LocationItems(data = state.data) {
                                viewModel.proceedIntent(
                                    CityFinderIntents.OnCityClick(
                                        location = state.data[it]
                                    )
                                )
                            }
                        }
                    }
                }


            }

        }



}

@Composable
private fun SearchBox(
    value: String,
    placeHolder: String = stringResource(R.string.search),
    onValueChange: (String) -> Unit,
) {


    OutlinedTextField(
        singleLine = true,
        maxLines = 1,
        shape = RoundedShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.fillMaxWidth(),
        value = value,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Search, "")
        },
        onValueChange = {
            onValueChange(it)
        }, placeholder = {
            Text(text = placeHolder)
        })
}


@Composable
private fun LoadingProgressBar(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LocationItems(
    modifier: Modifier = Modifier,
    data: List<LocationInfo>,
    onLocationClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                RoundedShape
            ),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(data) { index, city ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLocationClick(index)
                    }
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(text = city.cityName?:"")
            }
            if (index != data.lastIndex) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.5f
                    )
                )
            }
        }
    }
}

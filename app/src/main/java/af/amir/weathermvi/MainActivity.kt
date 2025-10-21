package af.amir.weathermvi

import af.amir.weathermvi.navigation.WeatherNavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import af.amir.weathermvi.presentation.theme.WeatherTheme
import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme(darkTheme = isSystemInDarkTheme()) {

                val viewModel: MainViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsState()

                splash.setKeepOnScreenCondition { !uiState.isReady }

                var showGpsOffDialog by remember { mutableStateOf(false) }
                var showLocationPermissionDialog by remember { mutableStateOf(false) }

                if (uiState.isReady){

                    WeatherNavGraph(
                        navController = rememberNavController(),
                        startDestination = uiState.startDestination!!,
                        onTurnOnGps = {
                            showGpsOffDialog = true
                        },
                        onGetLocationPermission = {
                            showLocationPermissionDialog = true
                        }
                    )
                }

                if (showGpsOffDialog) {
                    ShowGpsOffDialog(this) {
                        showGpsOffDialog = false
                    }
                }

                if (showLocationPermissionDialog) {
                    HandleLocationPermission {
                        showLocationPermissionDialog = false
                    }
                }

            }
        }
    }

    @Composable
    private fun ShowGpsOffDialog(
        context: Context,
        onDismissRequest: () -> Unit,
    ) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    openLocationSettings(context)
                    onDismissRequest()
                }) {
                    Text(getString(R.string.gps_off_dialog_positive_button))
                }
            }, dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(getString(R.string.gps_off_dialog_negative_button))
                }
            },
            title = {
                Text(getString(R.string.gps_is_off))
            },
            text = {
                Text(getString(R.string.gps_off_dialog_message))
            })

    }

    private fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }


    @Composable
    private fun HandleLocationPermission(onDismissRequest: () -> Unit) {
        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (shouldShowRationale) {
            ShowGoToSettingDialog(onDismissRequest)
        } else {
            ShowRationalDialog(onDismissRequest)
        }
    }

    @Composable
    private fun ShowRationalDialog(
        onDismissRequest: () -> Unit,
    ) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    requestLocationPermission()
                    onDismissRequest()
                }) {
                    Text(stringResource(R.string.dialog_allow))
                }
            }, dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            },
            title = {
                Text(stringResource(R.string.location_rational_dialog_title))
            },
            text = {
                Text(stringResource(R.string.location_rational_dialog_text))
            })

    }

    @Composable
    private fun ShowGoToSettingDialog(
        onDismissRequest: () -> Unit,
    ) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    goToAppSettings()
                    onDismissRequest()
                }) {
                    Text(stringResource(R.string.location_gotosetting_dialog_positive))
                }
            }, dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            },
            title = {
                Text(stringResource(R.string.location_gotosetting_dialog_title))
            },
            text = {
                Text(stringResource(R.string.location_gotosetting_dialog_text))
            })

    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun goToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}





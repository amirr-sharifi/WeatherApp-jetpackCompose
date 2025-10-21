package af.amir.weathermvi.navigation

import af.amir.weathermvi.navigation.NavDestinations.Companion.CITY_NAME
import af.amir.weathermvi.presentation.cityFinder.CityFinderScreen
import af.amir.weathermvi.presentation.locationwelcome.LocationWelcomeScreen
import af.amir.weathermvi.presentation.weather.WeatherScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    startDestination :String,
    onGetLocationPermission: () -> Unit,
    onTurnOnGps: () -> Unit,
) {

    NavHost(navController = navController, startDestination =startDestination) {
        composable(route = NavDestinations.Weather.route) {
            WeatherScreen(
                onEditCurrentLocation = { cityName ->
                    navController.navigate(
                        NavDestinations.CityFinder.createRoute(
                            cityName
                        )
                    )
                },
            )
        }

        composable(route = NavDestinations.LocationWelcome.route) {
            LocationWelcomeScreen {
                navController.navigate(NavDestinations.CityFinder.createRoute("")) {
                    popUpTo(NavDestinations.CityFinder.route) { inclusive = true }
                }
            }
        }

        composable(route = NavDestinations.CityFinder.route, arguments = listOf(
            navArgument(name = CITY_NAME) {
                type = NavType.StringType
                defaultValue = ""
            }
        )) { backStackEntry ->

            val cityName = backStackEntry.arguments?.getString(CITY_NAME)
            CityFinderScreen(
                currentCityName = cityName,
                onGetLocationPermission = onGetLocationPermission,
                onTurnOnGps = onTurnOnGps,
                onNavigateToWeatherScreen = {
                    navController.navigate(NavDestinations.Weather.route) {
                        popUpTo(0) { inclusive = true } // استک رو کامل خالی کن
                        launchSingleTop = true // از ساخت دوباره Weather جلوگیری کن اگر بالاست
                    }
                },
                onNavigateBack = { navController.popBackStack() })
        }

    }

}
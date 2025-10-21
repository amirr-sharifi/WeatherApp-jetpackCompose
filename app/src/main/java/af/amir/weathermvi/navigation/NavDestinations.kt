package af.amir.weathermvi.navigation

import android.net.Uri

sealed class NavDestinations(val route : String) {

    data object Weather : NavDestinations("weather")
    data object LocationWelcome : NavDestinations("locationWelcome")

    data object CityFinder : NavDestinations("cityFinder/{$CITY_NAME}"){
        fun createRoute(cityName:String):String{
            return "cityFinder/$cityName"
        }
    }


    companion object{
        const val CITY_NAME = "CityName"
    }
}
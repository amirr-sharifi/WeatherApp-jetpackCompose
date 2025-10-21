package af.amir.weathermvi

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application(){
    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()
    }
}
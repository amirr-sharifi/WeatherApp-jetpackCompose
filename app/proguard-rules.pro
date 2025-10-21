# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


############ Compose ############
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

############ Hilt / Dagger ############
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }

-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class androidx.hilt.lifecycle.ViewModelFactory** { *; }
-dontwarn dagger.hilt.internal.**

############ Retrofit ############
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# نگه داشتن API اینترفیس‌ها
-keep interface af.amir.weathermvi.data.remote.** { *; }

############ GSON (اگر استفاده می‌کنی) ############
-keep class af.amir.weathermvi.model.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

############ Room ############
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# نگه داشتن entity و DAO
-keep class af.amir.weathermvi.data.local.** { *; }

############ Coil ############
-keep class coil.** { *; }
-dontwarn coil.**

############ Lottie ############
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

############ ThreeTenABP ############
-keep class org.threeten.bp.** { *; }
-dontwarn org.threeten.bp.**

############ Google Play Location Services ############
-keep class com.google.android.gms.location.** { *; }
-dontwarn com.google.android.gms.location.**

############ SplashScreen ############
-keep class androidx.core.splashscreen.** { *; }
-dontwarn androidx.core.splashscreen.**

############ General AndroidX Safety ############
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

-keep class androidx.activity.** { *; }
-keep class androidx.fragment.app.** { *; }
-keep class androidx.appcompat.** { *; }
-keep class androidx.core.** { *; }

############ ViewModel (برای Hilt و Navigation) ############
-keep class * extends androidx.lifecycle.ViewModel

############ Kotlin Metadata ############
-keepclassmembers class ** {
    @kotlin.Metadata *;
}

############ Optional: جلوگیری از حذف Logcat هنگام Debug (اختیاری) ############
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

-dontwarn com.google.j2objc.annotations.RetainedWith
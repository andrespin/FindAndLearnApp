package android.findandlearnapp

import android.content.Context
import android.findandlearnapp.utils.loadLocale
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import java.util.*

// http://developine.com/android-app-language-change-localization-programatically-kotlin-example/

// https://lokalise.com/blog/kotlin-internationalization/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadLocale(this)
    }

//    private fun loadLocale() {
//        val sharedPref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
//        val localeToSet: String = sharedPref.getString("locale_to_set", "")!!
//        setLocale(localeToSet)
//    }
//
//    private fun setLocale(localeToSet: String) {
//        val localeListToSet = LocaleList(Locale(localeToSet))
//        LocaleList.setDefault(localeListToSet)
//
//        resources.configuration.setLocales(localeListToSet)
//        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
//
//        val sharedPref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//        sharedPref.putString("locale_to_set", localeToSet)
//        sharedPref.apply()
//    }

}
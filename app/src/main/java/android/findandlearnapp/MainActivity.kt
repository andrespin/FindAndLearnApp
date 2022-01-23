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
}
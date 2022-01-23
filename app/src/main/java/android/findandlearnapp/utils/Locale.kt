package android.findandlearnapp.utils

import android.content.Context
import android.os.LocaleList
import java.util.Locale

fun loadLocale(context: Context) {
    val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    val localeToSet: String = sharedPref.getString("locale_to_set", "")!!
    setLocale(localeToSet, context)
}

fun getCurrentLocale(context: Context): String {
    val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    return sharedPref.getString("locale_to_set", "")!!
}

fun setLocale(localeToSet: String, context: Context) {
    val localeListToSet = LocaleList(Locale(localeToSet))
    LocaleList.setDefault(localeListToSet)

    context.resources.configuration.setLocales(localeListToSet)
    context.resources.updateConfiguration(context.resources.configuration, context.resources.displayMetrics)

    val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
    sharedPref.putString("locale_to_set", localeToSet)
    sharedPref.apply()
}
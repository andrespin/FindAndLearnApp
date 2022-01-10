package android.findandlearnapp

import android.app.Application
import android.findandlearnapp.database.WordDatabase
import android.findandlearnapp.di.AppComponent
import android.findandlearnapp.di.AppModule
import android.findandlearnapp.di.DaggerAppComponent
import androidx.room.Room


class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this


        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}
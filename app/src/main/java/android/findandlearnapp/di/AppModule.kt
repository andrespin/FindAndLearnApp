package android.findandlearnapp.di


import android.findandlearnapp.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

}
package android.findandlearnapp.di


import android.findandlearnapp.dictionary.DictionaryViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)
interface AppComponent {

    fun inject(dictionaryViewModel: DictionaryViewModel)

}


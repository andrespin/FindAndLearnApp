package android.findandlearnapp.di


import android.findandlearnapp.dictionary.DictionaryViewModel
import android.findandlearnapp.words_manager.WordsManagerViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        RepoModule::class,
        DatabaseModule::class,
        SchedulerModule::class
    ]
)
interface AppComponent {

    fun inject(dictionaryViewModel: DictionaryViewModel)

    fun inject(wordsManagerViewModel: WordsManagerViewModel)

}


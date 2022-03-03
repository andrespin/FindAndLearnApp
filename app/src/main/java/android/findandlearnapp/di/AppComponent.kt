package android.findandlearnapp.di


import android.findandlearnapp.dialogs.ListsDialogViewModel
import android.findandlearnapp.dictionary.DictionaryViewModel
import android.findandlearnapp.words_manager.WordsManagerViewModel
import android.findandlearnapp.words_manager.mylists.MyListsViewModel
import android.findandlearnapp.words_test.TestViewModel
import android.findandlearnapp.words_test.WordTestViewModel
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

    fun inject(testViewModel: TestViewModel)

    fun inject(listsDialogViewModel : ListsDialogViewModel)

    fun inject(myListsViewModel : MyListsViewModel)

    fun inject(wordTestViewModel: WordTestViewModel)

}


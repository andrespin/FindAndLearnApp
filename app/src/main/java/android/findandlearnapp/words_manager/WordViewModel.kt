package android.findandlearnapp.words_manager

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.findandlearnapp.utils.convertToAddedWord
import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WordViewModel : BaseViewModel<AppState>() {



    @Inject
    lateinit var provideWordRepo: IWordRepo

    fun getWordFromDb(word: String) {

        provideWordRepo.findWordInDatabase(word)
            .subscribeOn(Schedulers.io())
            .subscribe {

            }
    }




    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }

}
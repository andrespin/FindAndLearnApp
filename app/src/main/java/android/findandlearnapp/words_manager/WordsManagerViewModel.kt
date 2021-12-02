package android.findandlearnapp.words_manager

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WordsManagerViewModel : BaseViewModel<AppState>() {

    val liveDataWords: MutableLiveData<List<WordEntity>> = MutableLiveData()

    @Inject
    lateinit var provideWordRepo: IWordRepo

    fun getAllWordsFromDb() {
        provideWordRepo.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                liveDataWords.postValue(repos)
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            })
    }


    /*
        private fun getWordTranslationRuEn(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        wordTranslationRepo.getWordTranslationRepo(
            yandexDictionaryKey,
            word_translation_from_Russian_to_English,
            word
        ).observeOn(Schedulers.io())
            .subscribe({ repos ->
                _mutableLiveData.postValue(
                    AppState.Success(
                        convertToWord(repos)
                    )
                )
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            }
            )
    }
     */


    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }
}
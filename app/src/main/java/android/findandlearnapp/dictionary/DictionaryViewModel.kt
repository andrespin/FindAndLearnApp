package android.findandlearnapp.dictionary

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.base.StringLanguage
import android.findandlearnapp.base.getLanguageOfWord
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.data.IWordTranslationRepo
import android.findandlearnapp.utils.convertToWord
import android.findandlearnapp.utils.word_translation_from_English_to_Russian
import android.findandlearnapp.utils.yandexDictionaryKey
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DictionaryViewModel : BaseViewModel<AppState>() {

    val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun translateTheWord(word: String) {
        if (getLanguageOfWord(word) == StringLanguage.Russian)
            getWordTranslationRuEn(word)
        if (getLanguageOfWord(word) == StringLanguage.English)
            getWordTranslationEnRu(word)
    }

    @Inject
    lateinit var wordTranslationRepo: IWordTranslationRepo

    private fun getWordTranslationEnRu(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        wordTranslationRepo.getWordTranslationRepo(
            yandexDictionaryKey,
            word_translation_from_English_to_Russian,
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

    private fun getWordTranslationRuEn(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        wordTranslationRepo.getWordTranslationRepo(
            yandexDictionaryKey,
            word_translation_from_English_to_Russian,
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


    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }


}
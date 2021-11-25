package android.findandlearnapp.dictionary

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.dictionary.data.DictionaryAndCardsAppState
import android.findandlearnapp.dictionary.data.IWordTranslationRepo
import android.findandlearnapp.utils.word_translation_from_English_to_Russian
import android.findandlearnapp.utils.yandexDictionaryKey
import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DictionaryViewModel : BaseViewModel<DictionaryAndCardsAppState>(){


    @Inject
    lateinit var wordTranslationRepo: IWordTranslationRepo

    fun getWordTranslationEnRu(word: String) {

        wordTranslationRepo.getWordTranslationRepo(
            yandexDictionaryKey,
            word_translation_from_English_to_Russian,
            word
        ).observeOn(Schedulers.io())
            .subscribe({ repos ->
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
            }
            )
    }

    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }


}
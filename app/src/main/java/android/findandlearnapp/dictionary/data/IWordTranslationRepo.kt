package android.findandlearnapp.dictionary.data

import io.reactivex.rxjava3.core.Single

interface IWordTranslationRepo {

    fun getWordTranslationRepo(
        key: String,
        language: String,
        word: String
    ): Single<WordTranslationServerResponse>

}
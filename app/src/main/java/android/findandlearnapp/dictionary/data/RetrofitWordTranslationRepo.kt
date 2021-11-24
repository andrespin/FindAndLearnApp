package android.findandlearnapp.dictionary.data

import android.dictionaryandcardsapp.utils.INetworkStatus
import io.reactivex.rxjava3.core.Single

class RetrofitWordTranslationRepo(
    private val api: DictionaryApi,
    private val networkStatus: INetworkStatus
) : IWordTranslationRepo {

    override fun getWordTranslationRepo(
        key: String,
        language: String,
        word: String
    ): Single<WordTranslationServerResponse> =
        api.getWordTranslation(key, language, word)

}
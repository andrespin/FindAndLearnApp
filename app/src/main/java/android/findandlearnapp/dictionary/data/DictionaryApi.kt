package android.findandlearnapp.dictionary.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    // https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=API-ключ&lang=en-ru&text=time

    // https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20211108T114933Z.627c90655b1289e9.563c5fc5563aebf24ed9d4a3f22b32a6f4e10f29&lang=en-ru&text=time

    @GET("dicservice.json/lookup?")
    fun getWordTranslation(
        @Query("key") key: String,
        @Query("lang") language: String,
        @Query("text") word: String
    ): Single<WordTranslationServerResponse>

}
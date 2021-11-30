package android.findandlearnapp.dictionary.repository

import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.data.WordTranslationServerResponse
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface IWordRepo {

    fun getWordTranslationRepo(
        key: String,
        language: String,
        word: String
    ): Single<WordTranslationServerResponse>

    fun addWordToDatabase(wordEntity: WordEntity)

    fun deleteWordFromDatabase(wordEntity: WordEntity)

    fun deleteAllWordsFromDatabase()

    fun getAllWordsFromDatabase(): Single<List<WordEntity>>

    fun findWordInDatabase(textOrig: String): Maybe<WordEntity>

}
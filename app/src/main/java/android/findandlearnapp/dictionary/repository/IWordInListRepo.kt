package android.findandlearnapp.dictionary.repository

import android.findandlearnapp.database.WordInListEntity
import io.reactivex.rxjava3.core.Single

interface IWordInListRepo {

    fun addWordInListToDatabase(wordInListEntity: WordInListEntity)

    fun deleteWordInListFromDatabase(textOrig: String)

    fun deleteAllWordsInListFromDatabase()

    fun getAllWordsInListFromDatabase(): Single<List<WordInListEntity>>

    fun findWordInListInDatabase(textOrig: String): Single<WordInListEntity>

}
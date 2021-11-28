package android.findandlearnapp.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WordDao {

    @Insert
    fun insertWord(word: WordEntity)

    @Delete
    fun deleteWord(word: WordEntity): Completable

    @Query("SELECT * FROM words_table")
    fun getAllWords(): Single<List<WordEntity>>

    @Query("DELETE FROM words_table")
    fun deleteAllWords(): Completable

    @Query("SELECT * FROM words_table WHERE textOrig=:textOrig")
    fun getWord(textOrig: String): Single<WordEntity>


}
package android.findandlearnapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface WordInListDao {

    @Insert
    fun insertWord(wordInListEntity: WordInListEntity)

    @Delete
    fun deleteWord(wordInListEntity: WordInListEntity)

    @Query("SELECT * FROM words_in_list_table")
    fun getAllWords(): Single<List<WordInListEntity>>

    @Query("DELETE FROM words_in_list_table")
    fun deleteAllWords(): Completable

    @Query("SELECT * FROM words_in_list_table WHERE textOrig=:textOrig")
    fun getWord(textOrig: String): Single<WordInListEntity>

}
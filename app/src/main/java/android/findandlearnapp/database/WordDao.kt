package android.findandlearnapp.database

import androidx.room.*
//import io.reactivex.Completable
//import io.reactivex.Single

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface WordDao {

    @Insert
    fun insertWord(wordEntity: WordEntity)

    @Delete
    fun deleteWord(wordEntity: WordEntity)

    @Query("SELECT * FROM words_table")
    fun getAllWords(): Single<List<WordEntity>>

    @Query("DELETE FROM words_table")
    fun deleteAllWords(): Completable

    @Query("SELECT * FROM words_table WHERE textOrig=:textOrig")
    fun getWord(textOrig: String): Single<WordEntity>

//    @Query("SELECT * FROM words_table WHERE textOrig=:textOrig")
//    fun getWord(textOrig: String): Single<WordEntity>

}
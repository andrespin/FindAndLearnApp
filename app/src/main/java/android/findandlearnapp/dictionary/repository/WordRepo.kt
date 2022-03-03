package android.findandlearnapp.dictionary.repository

import android.findandlearnapp.database.WordDao
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.database.WordInListDao
import android.findandlearnapp.database.WordInListEntity
import android.findandlearnapp.dictionary.data.DictionaryApi
import android.findandlearnapp.dictionary.data.WordTranslationServerResponse
import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

// https://medium.com/@volodya.vechirko/android-repository-implementation-rxjava2-room-retrofit-59dbd4b238c4

class WordRepo @Inject constructor(
    private val wordDao: WordDao,
    private val wordInListDao: WordInListDao,
    private val api: DictionaryApi
) : IWordRepo, IWordInListRepo {

    override fun getWordTranslationRepo(
        key: String,
        language: String,
        word: String
    ): Single<WordTranslationServerResponse> =
        api.getWordTranslation(key, language, word)

    override fun addWordToDatabase(wordEntity: WordEntity) {
        wordDao.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                println(repos)
                if (!repos.contains(wordEntity)) {
                    Completable.fromRunnable {
                        wordDao.insertWord(wordEntity)
                    }.subscribeOn(Schedulers.io()).subscribe()
                    Log.d("${wordEntity.textOrig} status", "Added")
                    seeWords()
                }
            }, {
                println("addWordToDatabaseError")
                Log.d("Error: ", it.message!!)
            })
    }

    override fun deleteWordFromDatabase(textOrig: String) {
        println("deleteWordFromDatabase bef")
        wordDao.getWord(textOrig).subscribeOn(Schedulers.io())
            .subscribe({
                println("deleteWordFromDatabase" + it)
                Completable.fromRunnable {
                    wordDao.deleteWord(it)
                }.subscribeOn(Schedulers.io()).subscribe()
                Log.d("${it.textOrig} status repo", "Deleted")
                seeWords()
            }, {
                println("deleteWordFromDatabaseError")
                Log.d("Error: ", it.message!!)
            })
    }

    override fun deleteAllWordsFromDatabase() {
        Completable.fromRunnable {
            wordDao.deleteAllWords()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun getAllWords(): Single<List<WordEntity>> = wordDao.getAllWords()

    private fun seeWords() {
        wordDao.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    override fun getAllWordsFromDatabase(): Single<List<WordEntity>> = wordDao.getAllWords()

    override fun findWordInDatabase(textOrig: String): Single<WordEntity> =
        wordDao.getWord(textOrig)

    override fun addWordInListToDatabase(wordInListEntity: WordInListEntity) {
        wordInListDao.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                println(repos)

                if (!repos.contains(wordInListEntity)) {
                    Completable.fromRunnable {
                        wordInListDao.insertWord(wordInListEntity)
                    }.subscribeOn(Schedulers.io()).subscribe()

                    Log.d("${wordInListEntity.textOrig} status", "Added")
                }
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    override fun deleteWordInListFromDatabase(textOrig: String) {
        println("deleteWordFromDatabase bef")
        wordInListDao.getWord(textOrig).subscribeOn(Schedulers.io())
            .subscribe({
                Completable.fromRunnable {
                    wordInListDao.deleteWord(it)
                }.subscribeOn(Schedulers.io()).subscribe()
                Log.d("${it.textOrig} status repo", "Deleted")
                seeWords()
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    override fun deleteAllWordsInListFromDatabase() {
        Completable.fromRunnable {
            wordInListDao.deleteAllWords()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun getAllWordsInListFromDatabase(): Single<List<WordInListEntity>> =
        wordInListDao.getAllWords()

    override fun findWordInListInDatabase(textOrig: String): Single<WordInListEntity> =
        wordInListDao.getWord(textOrig)

}
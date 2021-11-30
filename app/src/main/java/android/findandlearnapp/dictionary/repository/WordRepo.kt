package android.findandlearnapp.dictionary.repository

import android.findandlearnapp.database.WordDao
import android.findandlearnapp.database.WordEntity
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
    private val api: DictionaryApi
) : IWordRepo {

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
                }
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    override fun deleteWordFromDatabase(wordEntity: WordEntity) {
        wordDao.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                println(repos)
                if (repos.contains(wordEntity)) {
                    Completable.fromRunnable {
                        wordDao.deleteWord(wordEntity)
                    }.subscribeOn(Schedulers.io()).subscribe()
                }
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    override fun deleteAllWordsFromDatabase() {
        Completable.fromRunnable {
            wordDao.deleteAllWords()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun getAllWordsFromDatabase(): Single<List<WordEntity>> = wordDao.getAllWords()

    override fun findWordInDatabase(textOrig: String): Maybe<WordEntity> = wordDao.getWord(textOrig)

}
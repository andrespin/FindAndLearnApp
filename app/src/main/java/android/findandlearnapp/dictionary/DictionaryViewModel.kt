package android.findandlearnapp.dictionary


import android.findandlearnapp.R
import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.base.StringLanguage
import android.findandlearnapp.base.getLanguageOfWord
import android.findandlearnapp.database.WordDao
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.data.IWordTranslationRepo
import android.findandlearnapp.dictionary.data.Word
import android.findandlearnapp.utils.convertToWord
import android.findandlearnapp.utils.word_translation_from_English_to_Russian
import android.findandlearnapp.utils.yandexDictionaryKey
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class DictionaryViewModel : BaseViewModel<AppState>() {

    private var isWordAdded = false

    private lateinit var wordDao: WordDao

    val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    val liveDataImgPutWordToDb = MutableLiveData<Event<AddWordToDbImageData>>()

    fun translateTheWord(word: String) {
        if (getLanguageOfWord(word) == StringLanguage.Russian)
            getWordTranslationRuEn(word)
        if (getLanguageOfWord(word) == StringLanguage.English)
            getWordTranslationEnRu(word)
    }

    @Inject
    lateinit var provideWordDao: WordDao

    fun addWordToDb(word: Word) {

        val wordEntity = WordEntity(word.textOrig, word.txtPhonetics)

        Log.d("Status", "Works")


        /*
                bookDao.getAllBooks().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                println("repos $repos")
            },
                {
                    Log.d("Error: ", it.message!!)
                })
         */

        provideWordDao.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                if (!repos.contains(WordEntity(word.textOrig, word.txtPhonetics))) {
                    Log.d("Status", "Does not contain")
                    Completable.fromRunnable {
                        provideWordDao.insertWord(WordEntity(word.textOrig, word.txtPhonetics))
                    }.subscribeOn(Schedulers.io()).subscribe()
                } else {
                    Log.d("Status", "Contain")
                }

                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            }
            )

//        var words = provideWordDao.getAllWords()

        /*
        .observeOn(Schedulers.io())
            .subscribe({ repos ->
                _mutableLiveData.postValue(
                    AppState.Success(
                        convertToWord(repos)
                    )
                )
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            }
            )
         */


        /*
                Completable.fromRunnable {
            bookDao.insertBook(RxBook(3, "name", "auth"))
        }.subscribeOn(Schedulers.io()).subscribe()
         */

        // TODO Add word to Db
        isWordAdded = checkIfAddedToDb()
        if (isWordAdded) {
            liveDataImgPutWordToDb.postValue(
                Event(
                    AddWordToDbImageData(
                        View.VISIBLE,
                        R.drawable.ic_tick
                    )
                )
            )
        }
    }


    @Inject
    lateinit var wordTranslationRepo: IWordTranslationRepo

    private fun getWordTranslationEnRu(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        wordTranslationRepo.getWordTranslationRepo(
            yandexDictionaryKey,
            word_translation_from_English_to_Russian,
            word
        ).observeOn(Schedulers.io())
            .subscribe({ repos ->
                val word = convertToWord(repos)
                if (word.isFound) {
                    _mutableLiveData.postValue(
                        AppState.Success(
                            word
                        )
                    )
                    liveDataImgPutWordToDb.postValue(
                        Event(
                            AddWordToDbImageData(
                                View.VISIBLE,
                                R.drawable.ic_plus
                            )
                        )
                    )
                } else {
                    liveDataImgPutWordToDb.postValue(
                        Event(
                            AddWordToDbImageData(
                                View.GONE,
                                R.drawable.ic_plus
                            )
                        )
                    )
                    _mutableLiveData.postValue(
                        AppState.Success(
                            word
                        )
                    )
                }
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            }
            )
    }

    private fun getWordTranslationRuEn(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        wordTranslationRepo.getWordTranslationRepo(
            yandexDictionaryKey,
            word_translation_from_English_to_Russian,
            word
        ).observeOn(Schedulers.io())
            .subscribe({ repos ->
                _mutableLiveData.postValue(
                    AppState.Success(
                        convertToWord(repos)
                    )
                )
                println(repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            }
            )
    }

    private fun checkIfAddedToDb(): Boolean {
        // TODO Find word in db
        return true
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

}
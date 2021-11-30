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
import android.findandlearnapp.dictionary.repository.IWordRepo
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
    lateinit var provideWordRepo: IWordRepo

    fun wordsManager(word: Word, isAdded: Boolean) =
        if (isAdded)
            deleteWordFromDb(word)
        else
            addWordToDb(word)

    private fun addWordToDb(word: Word) {
        provideWordRepo.addWordToDatabase(WordEntity(word.textOrig, word.txtPhonetics))
        setAddWordImage(View.VISIBLE, R.drawable.ic_tick, wordAdded)


//        provideWordRepo.findWordInDatabase(word.textOrig)
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                setAddWordImage(View.VISIBLE, R.drawable.ic_tick, wordAdded)
//            }, {
//                handleError(it)
//            })

    }

    private fun deleteWordFromDb(word: Word) {
        provideWordRepo.deleteWordFromDatabase(word.textOrig)
        setAddWordImage(View.VISIBLE, R.drawable.ic_plus, wordNotAdded)

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
                    findWordInDatabase(word)
                    setAddWordImage(View.VISIBLE, R.drawable.ic_plus, wordNotAdded)
                } else {
                    setAddWordImage(View.GONE, R.drawable.ic_plus, wordNotAdded)
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

    private fun findWordInDatabase(word: Word) {

        println("findWordInDatabase bef")

        provideWordRepo.findWordInDatabase(word.textOrig)
            .observeOn(Schedulers.io())
            .subscribe {
                println("findWordInDatabase" + it)
                setAddWordImage(View.VISIBLE, R.drawable.ic_tick, wordAdded)
            }

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


        return true
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    private fun setAddWordImage(visibility: Int, drawable: Int, isWordAdded: Boolean) {
        liveDataImgPutWordToDb.postValue(
            Event(
                AddWordToDbImageData(
                    visibility,
                    drawable,
                    isWordAdded
                )
            )
        )
    }


}
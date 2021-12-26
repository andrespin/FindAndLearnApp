package android.findandlearnapp.words_manager

import android.content.Context
import android.findandlearnapp.R
import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.Event
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.findandlearnapp.utils.addedWordIsChecked
import android.findandlearnapp.utils.addedWordIsNotChecked
import android.findandlearnapp.utils.convertToAddedWord
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment.findNavController
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

data class AddedWord(
    val wordEntity: WordEntity,
    var background: Int,
    var isLongClick: Boolean? = null,
    val position: Int
)

data class Visibility(val visibility: Int)

class WordsManagerViewModel : BaseViewModel<AppState>() {

    val liveDataWords: MutableLiveData<List<AddedWord>> = MutableLiveData()

    val liveDataCheckWord = MutableLiveData<Event<AddedWord>>()

    val liveDataCheckedWords = MutableLiveData<Event<List<AddedWord>>>()

    val liveDataButtonsVisibility = MutableLiveData<Event<Visibility>>()

    val liveDataNavigationEvent = MutableLiveData<Event<Boolean>>()

    private var addedWordsList = mutableListOf<AddedWord>()

    var isChecked = false

    fun deleteAddedWords() {
        for (i in 0 until addedWordsList.size) {
            if (addedWordsList[i].isLongClick!!) {
                provideWordRepo.deleteWordFromDatabase(
                    addedWordsList[i].wordEntity.textOrig
                )
                addedWordsList.remove(addedWordsList[i])
                liveDataWords.postValue(addedWordsList)
            }
        }
        findCheckedWords(addedWordsList)
        setButtonsVisibility()
    }

    @Inject
    lateinit var provideWordRepo: IWordRepo

    fun getAllWordsFromDb() {
        provideWordRepo.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                liveDataWords.postValue(convertToAddedWord(repos))
                println(repos)

            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            })
    }

    private fun findCheckedWords(word: List<AddedWord>) {
        var bool = false
        for (i in 0 until word!!.size) {
            println("word[i].isLongClick: ${word[i].isLongClick}")
            if (word[i].isLongClick!!) {
                bool = true
            }
        }
        isChecked = bool
        println("isChecked added word: $isChecked")
    }

    fun itemViewOnClickListener(addedWord: AddedWord) {
        if (isChecked) {
            checkWord(addedWord)
        } else {
            liveDataNavigationEvent.postValue(Event(true))
        }
    }

    fun itemViewOnLongClickListener(addedWord: AddedWord) {
        checkWord(addedWord)
    }

    fun setAddedWords(list: MutableList<AddedWord>) {
        addedWordsList = list
    }

    fun setAddedWord(word: AddedWord) {
        addedWordsList[word.position] = word
        println("added word is checked: ${word.isLongClick}")
    }

    fun setAddedWordsUnchecked() {
        for (i in 0 until addedWordsList.size) {
            addedWordsList[i].isLongClick = false
            addedWordsList[i].background = addedWordIsNotChecked
        }
        findCheckedWords(addedWordsList)
        setButtonsVisibility()
        liveDataCheckedWords.postValue(Event(addedWordsList))
    }

    private fun setButtonsVisibility() {
        if (isChecked) {
            liveDataButtonsVisibility.postValue(Event(Visibility(View.VISIBLE)))
        } else {
            liveDataButtonsVisibility.postValue(Event(Visibility(View.INVISIBLE)))
        }
    }

    private fun checkWord(addedWord: AddedWord) {
        when (addedWord.isLongClick) {
            true -> {
                addedWord.isLongClick = false
                addedWord.background = addedWordIsNotChecked
                setAddedWord(addedWord)
            }
            false -> {
                addedWord.isLongClick = true
                addedWord.background = addedWordIsChecked
                setAddedWord(addedWord)
            }
        }

        Log.d("is Checked", isChecked.toString())
        liveDataCheckWord.postValue(
            Event(
                addedWord
            )
        )
        findCheckedWords(addedWordsList)
        setButtonsVisibility()
    }

    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }
}
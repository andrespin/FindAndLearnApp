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
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

data class AddedWord(
    val wordEntity: WordEntity,
    var background: Int,
    var isLongClick: Boolean? = null,
    val position: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("wordEntity"),
        parcel.readInt(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(background)
        parcel.writeValue(isLongClick)
        parcel.writeInt(position)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddedWord> {
        override fun createFromParcel(parcel: Parcel): AddedWord {
            return AddedWord(parcel)
        }

        override fun newArray(size: Int): Array<AddedWord?> {
            return arrayOfNulls(size)
        }
    }
}

data class Visibility(val visibility: Int)

class WordsManagerViewModel : BaseViewModel<AppState>() {

    val liveDataWords: MutableLiveData<List<AddedWord>> = MutableLiveData()

    val liveDataCheckWord = MutableLiveData<Event<AddedWord>>()

    val liveDataCheckedWords = MutableLiveData<Event<List<AddedWord>>>()

    val liveDataButtonsVisibility = MutableLiveData<Event<Visibility>>()

    val liveDataNavigationEvent = MutableLiveData<Event<AddedWord>>()

    private var addedWordsList = mutableListOf<AddedWord>()

    var isChecked = false

    fun deleteAddedWords() {
        val listOfWordsToDelete = mutableListOf<AddedWord>()
        for (i in 0 until addedWordsList.size) {
                println("i : $i, addedWordsList.size ${addedWordsList.size}")
                if (addedWordsList[i].isLongClick!!) {
                    provideWordRepo.deleteWordFromDatabase(
                        addedWordsList[i].wordEntity.textOrig
                    )
                    listOfWordsToDelete.add(addedWordsList[i])
                }
        }
        for (i in 0 until listOfWordsToDelete.size) {
            addedWordsList.remove(listOfWordsToDelete[i])
        }
        liveDataWords.postValue(addedWordsList)
        findCheckedWords(addedWordsList)
        setButtonsVisibility()
    }

    @Inject
    lateinit var provideWordRepo: IWordRepo

    fun getAllWordsFromDb() {
        provideWordRepo.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                liveDataWords.postValue(convertToAddedWord(repos))
                setAddedWords(convertToAddedWord(repos) as MutableList<AddedWord>)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            })
    }

    private fun findCheckedWords(word: List<AddedWord>) {
        var bool = false
        for (i in 0 until word!!.size) {
            if (word[i].isLongClick!!) {
                bool = true
            }
        }
        isChecked = bool
    }

    fun itemViewOnClickListener(addedWord: AddedWord) {
        if (isChecked) {
            checkWord(addedWord)
        } else {
            liveDataNavigationEvent.postValue(Event(addedWord))
        }
    }

    fun itemViewOnLongClickListener(addedWord: AddedWord) {
        checkWord(addedWord)
    }

    fun setAddedWords(list: MutableList<AddedWord>) {
        addedWordsList = list
    }

    fun setAddedWord(word: AddedWord) {
        println("addedWordsList.size : ${addedWordsList.size}")
        addedWordsList[word.position] = word
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
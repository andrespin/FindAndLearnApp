package android.findandlearnapp.words_manager

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.database.WordInListEntity
import android.findandlearnapp.dialogs.adapter.WordList
import android.findandlearnapp.dictionary.Event
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.repository.IWordInListRepo
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.findandlearnapp.utils.*
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

enum class LanguageOfWords() {
    Russian, English, All_words
}

data class Visibility(val visibility: Int)

class WordsManagerViewModel : BaseViewModel<AppState>() {

    val liveDataWords: MutableLiveData<List<AddedWord>> = MutableLiveData()

    val liveDataCheckWord = MutableLiveData<Event<AddedWord>>()

    val liveDataCheckedWords = MutableLiveData<Event<List<AddedWord>>>()

    val liveDataButtonsVisibility = MutableLiveData<Event<Visibility>>()

    val liveDataNavigationEvent = MutableLiveData<Event<AddedWord>>()

    val eventNavigationToWord = MutableLiveData<Event<AddedWord>>()

    val getWordsInList = MutableLiveData<List<AddedWord>>()

    private var addedWordsList = mutableListOf<AddedWord>()

    private var checkedAddedWordsList = mutableListOf<AddedWord>()

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


    fun deleteWordsInCurrentList() {
        val listOfWordsToDelete = mutableListOf<AddedWord>()
        for (i in 0 until addedWordsList.size) {
            if (addedWordsList[i].isLongClick!!) {
                provideWordInListRepo.deleteWordInListFromDatabase(
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

    fun getWordsInCurrentList(listName: String) {
        provideWordInListRepo.getAllWordsInListFromDatabase().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                val words = getWordsFromList(repos, listName)
                val myWords = convertToAddedWord(words, true) as MutableList<AddedWord>
                setAddedWords(
                    myWords
                )
                getWordsInList.postValue(myWords)
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    @Inject
    lateinit var provideWordRepo: IWordRepo

    fun getAllWordsFromDb(langOfWords: LanguageOfWords) {
        provideWordRepo.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                setWords(langOfWords, repos)
            }, {
                Log.d("Error: ", it.message!!)
                handleError(it)
            })
    }


    private fun setWords(langOfWords: LanguageOfWords, repos: List<WordEntity>) {
        when (langOfWords) {
            LanguageOfWords.Russian -> {
                val words = convertToAddedWord(getRusWords(repos))
                        as MutableList<AddedWord>
                setAddedWords(
                    words
                )
                liveDataWords.postValue(words)
            }

            LanguageOfWords.English -> {
                val words = convertToAddedWord(getEngWords(repos))
                        as MutableList<AddedWord>
                setAddedWords(
                    words
                )
                liveDataWords.postValue(words)

            }

            LanguageOfWords.All_words ->
                setAddedWords(convertToAddedWord(repos) as MutableList<AddedWord>)
        }

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

    private fun setAddedWords(list: MutableList<AddedWord>) {
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


    fun getCheckedWords() = checkedAddedWordsList

    fun getCheckedWords(getOnlyListOfStrings: Boolean): Array<String> {
        val list = mutableListOf<String>()
        for (i in 0 until checkedAddedWordsList.size) {
            list.add(checkedAddedWordsList[i].wordEntity.textOrig)
        }


        val strings = Array(list.size) { "" }

        for (i in 0 until list.size) {
            strings[i] = list[i]
        }
        return strings
    }

    @Inject
    lateinit var provideWordInListRepo: IWordInListRepo

    fun saveToList(list: WordList, words: List<AddedWord>) {
        for (i in 0 until words.size) {
            provideWordInListRepo.addWordInListToDatabase(
                convertToWordInListEntity(words[i].wordEntity, list.name)
            )
        }
    }

    private fun setButtonsVisibility() {
        if (isChecked) {
            liveDataButtonsVisibility.postValue(Event(Visibility(View.VISIBLE)))
        } else {
            liveDataButtonsVisibility.postValue(Event(Visibility(View.GONE)))
        }
    }

    private fun checkWord(addedWord: AddedWord) {

        when (addedWord.isLongClick) {
            true -> {
                checkedAddedWordsList.remove(addedWord)
                addedWord.isLongClick = false
                addedWord.background = addedWordIsNotChecked
                setAddedWord(addedWord)
            }
            false -> {
                checkedAddedWordsList.add(addedWord)
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
        error.printStackTrace()
    }
}
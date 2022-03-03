package android.findandlearnapp.words_manager.mylists

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.dialogs.adapter.WordList
import android.findandlearnapp.dictionary.Event
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.repository.IWordInListRepo
import android.findandlearnapp.utils.addedWordIsChecked
import android.findandlearnapp.utils.addedWordIsNotChecked
import android.findandlearnapp.words_manager.Visibility
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData

import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MyListsViewModel : BaseViewModel<AppState>() {

    val livaDataLists = MutableLiveData<MutableList<WordList>>()

    private var addedWordLists = mutableListOf<WordList>()

    private var checkedLists = mutableListOf<WordList>()

    val eventCheckList = MutableLiveData<Event<WordList>>()

    val eventSwitchButtonsVisibility = MutableLiveData<Event<Visibility>>()

    val eventNavigateToList = MutableLiveData<Event<String>>()

    var isChecked = false

    @Inject
    lateinit var provideWordInListRepo: IWordInListRepo

    fun getAllWordsInList() {
        provideWordInListRepo.getAllWordsInListFromDatabase().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                println("getAllWordsInList() - $repos")
                Log.d("getAllWordsInList()", repos.toString())
                val l = countNumberOfLists(repos)
                livaDataLists.postValue(l)
                setLists(l)
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    fun deleteCheckedLists() {
        val wordListsToDelete = mutableListOf<WordList>()
        for (i in 0 until addedWordLists.size) {
            if (addedWordLists[i].isLongClicked!!) {
                provideWordInListRepo.deleteWordInListFromDatabase(addedWordLists[i].name)
                wordListsToDelete.add(addedWordLists[i])
            }
        }
        for (i in 0 until wordListsToDelete.size) {
            addedWordLists.remove(wordListsToDelete[i])
        }

        livaDataLists.postValue(addedWordLists)
        setCheckedLists(addedWordLists)
        switchButtonsVisibility()
    }

    fun setAddedListsUnchecked() {
        for (i in 0 until addedWordLists.size) {
            addedWordLists[i].isLongClicked = false
            addedWordLists[i].background = addedWordIsNotChecked
        }
        setCheckedLists(addedWordLists)
        switchButtonsVisibility()
        livaDataLists.postValue(addedWordLists)
    }

    fun itemViewClickListener(wordList: WordList) {
        if (isChecked) {
            checkWordList(wordList)
        } else {
            eventNavigateToList.postValue(Event(wordList.name))
        }
    }

    fun itemViewOnLongClickListener(wordList: WordList) {
        checkWordList(wordList)
    }

    private fun checkWordList(wordList: WordList) {

        when (wordList.isLongClicked) {

            true -> {
                checkedLists.remove(wordList)
                wordList.isLongClicked = false
                wordList.background = addedWordIsNotChecked
                setAddedList(wordList)
            }

            false -> {

                println("wordList pos:  ${wordList.position}")

                println("addedWordLists.size :  ${addedWordLists.size}")

                checkedLists.add(wordList)

                wordList.isLongClicked = true
                wordList.background = addedWordIsChecked
                setAddedList(wordList)
            }

        }

        eventCheckList.postValue(
            Event(
                wordList
            )
        )
        setCheckedLists(addedWordLists)
        switchButtonsVisibility()
    }

    private fun switchButtonsVisibility() {
        if (isChecked) {
            eventSwitchButtonsVisibility.postValue(Event(Visibility(View.VISIBLE)))
        } else {
            eventSwitchButtonsVisibility.postValue(Event(Visibility(View.GONE)))
        }
    }


    fun setLists(lists: List<WordList>) {
        addedWordLists = lists as MutableList<WordList>
    }

    fun setAddedList(wordList: WordList) {
        addedWordLists[wordList.position!!] = wordList
    }

    private fun setCheckedLists(wordList: List<WordList>) {
        var bool = false
        for (i in 0 until wordList!!.size) {
            if (wordList[i].isLongClicked!!) {
                bool = true
            }
        }
        isChecked = bool
    }

    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }

}
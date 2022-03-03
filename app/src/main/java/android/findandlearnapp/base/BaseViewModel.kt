package android.findandlearnapp.base

import android.findandlearnapp.database.WordInListEntity
import android.findandlearnapp.dialogs.adapter.WordList
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.utils.addedWordIsNotChecked
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val wordAdded = true

    protected val wordNotAdded = false


    fun getWordsFromList(
        listOfLists: List<WordInListEntity>,
        listName: String
    ): List<WordInListEntity> {

        val wordsFromList = mutableListOf<WordInListEntity>()

        for (i in 0 until listOfLists.size) {
            if (listOfLists[i].listName == listName && listOfLists[i].textOrig != listName) {
                wordsFromList.add(listOfLists[i])
            }
        }
        return wordsFromList
    }

    fun countNumberOfLists(list: List<WordInListEntity>): MutableList<WordList> {

        val map = HashMap<String, Int>()

        val lists = mutableListOf<WordList>()

        for (i in 0 until list.size) {

            val name = list[i].listName

            var listNumber = 0

            // map[name] = listNumber

            for (j in 0 until list.size) {
                if (name == list[j].listName && name != "") {
                    map[name] = listNumber++
                }
            }
        }

        for ((key, value) in map) {
            lists.add(
                WordList(
                    key,
                    value
                )
            )
        }
        for (i in 0 until lists.size) {
            lists[i].position = i
            lists[i].isLongClicked = false
            lists[i].background = addedWordIsNotChecked
        }
        return lists
    }


    abstract fun handleError(error: Throwable)

}
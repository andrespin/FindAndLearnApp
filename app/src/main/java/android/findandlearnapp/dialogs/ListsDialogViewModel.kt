package android.findandlearnapp.dialogs

import android.findandlearnapp.base.BaseViewModel
import android.findandlearnapp.database.WordInListEntity
import android.findandlearnapp.dialogs.adapter.WordList
import android.findandlearnapp.dictionary.Event
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.repository.IWordInListRepo
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.util.Log
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import io.reactivex.rxjava3.schedulers.Schedulers

const val emptyWord = "emptyWordInListEntity"

data class ListNumberOfWords(val name: String, val number: Int)

class ListsDialogViewModel : BaseViewModel<AppState>() {

    private lateinit var wordsToSaveInList: List<String>

    @Inject
    lateinit var provideWordInListRepo: IWordInListRepo

    @Inject
    lateinit var provideWordRepo: IWordRepo

    val eventSelectListToSave = MutableLiveData<Event<WordList>>()

    val liveDataWordLists = MutableLiveData<List<WordInListEntity>>()

    val eventCreateNewList = MutableLiveData<Event<WordList>>()

    val eventUpdateAdapter = MutableLiveData<Event<WordList>>()

    val livaDataLists = MutableLiveData<MutableList<WordList>>()

    fun createNewList(wordList: WordList) {
        provideWordInListRepo.addWordInListToDatabase(
            WordInListEntity(
                wordList.name,
                wordList.name,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
    }

    fun itemViewOnClickListener(list: WordList) {
        eventSelectListToSave.value = Event(list)
    }

    fun initWordsToSave(words: Array<String>?) {
        val list = mutableListOf<String>()
        if (words != null) {
            for (i in 0 until words.size) {
                list.add(words[i])
            }
        }
        wordsToSaveInList = list
    }

    fun addListToAdapter(wordList: WordList) {
        eventUpdateAdapter.postValue(Event(wordList))
    }

    fun getAllWordsInList() {
        provideWordInListRepo.getAllWordsInListFromDatabase().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                livaDataLists.postValue(countNumberOfLists(repos))
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    fun saveWordsInList(wordList: WordList) {
        for (element in wordsToSaveInList) {
            provideWordRepo.findWordInDatabase(element).subscribeOn(Schedulers.io())
                .subscribe({ repos ->
                    provideWordInListRepo.addWordInListToDatabase(
                        WordInListEntity(
                            repos.textOrig,
                            wordList.name,
                            repos.txtPhonetics,
                            repos.translationsOfNoun,
                            repos.translationsOfPronoun,
                            repos.translationsOfAdjective,
                            repos.translationsOfVerb,
                            repos.translationsOfAdverb,
                            repos.translationsOfPreposition,
                            repos.translationsOfConjunction,
                            repos.translationsOfInterjection,
                            repos.translationsOfNumeral,
                            repos.translationsOfParticle,
                            repos.translationsOfInvariable,
                            repos.translationsOfParticiple,
                            repos.translationsOfAdverbialParticiple
                        )
                    )
                }, {
                    Log.d("Error: ", it.message!!)
                })
        }
        // TODO delete saved words from another list

    }


    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }


}
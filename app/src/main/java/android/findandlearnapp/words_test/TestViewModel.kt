package android.findandlearnapp.words_test

import android.findandlearnapp.App
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.Event
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.findandlearnapp.utils.EmptyField
import android.findandlearnapp.utils.convertToWordTranslationsList
import android.findandlearnapp.words_manager.AddedWord
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

class TestViewModel : ViewModel() {

    data class MyAddedWord(val textOrig: String, val translations: List<String>)

    data class WordsCard(
        val word1: String,
        val word2: String,
        val word3: String,
        val word4: String,
        val textOrig: String,
        val rightPosition: Int
    )

    val liveDataCreateCardEvent = MutableLiveData<Event<WordsCard>>()

    private lateinit var nounsList: List<String>
    private lateinit var verbsList: List<String>
    private lateinit var adverbsList: List<String>
    private lateinit var adjectivesList: List<String>

    private var myNouns = mutableListOf<MyAddedWord>()
    private var myVerbs = mutableListOf<MyAddedWord>()
    private var myAdverbs = mutableListOf<MyAddedWord>()
    private var myAdjectives = mutableListOf<MyAddedWord>()

    private lateinit var addedWordsList: List<WordEntity>

    private val partOfSpeechSet = setOf("nouns", "verbs", "adverbs", "adjectives")
    private val partsOfSpeechList = listOf("nouns", "verbs", "adverbs", "adjectives")
    
    fun initLists() {
        nounsList = convertToList(readTxtFile("ru_nouns.txt"))
        verbsList = convertToList(readTxtFile("ru_verbs.txt"))
        adverbsList = convertToList(readTxtFile("ru_adverbs.txt"))
        adjectivesList = convertToList(readTxtFile("ru_adjectives.txt"))

        println("nounsList : size ${nounsList.size}, $nounsList")
        println("verbsList : size ${verbsList.size}, $verbsList")
        println("adverbsList : size ${adverbsList.size}, $adverbsList")
        println("adjectivesList : size ${adjectivesList.size}, $adjectivesList")

        initMyLists()
    }

    fun createTestCard() {
        when (partOfSpeechSet.random()) {
            "nouns" -> {
                if (myNouns.size > 0) {
                    createWordRaw(myNouns, nounsList)
                }
            }
            "verbs" -> {

            }
            "adverbs" -> {

            }
            "adjectives" -> {

            }
        }

    }

    private fun createWordRaw(myWords: List<MyAddedWord>, words: List<String>) {
        val rightAnswerPosition = (0..3).random()
        val randomWordNumber = (0 until myWords.size).random()
        when (rightAnswerPosition) {
            0 -> {
                liveDataCreateCardEvent.postValue(
                    Event(
                        WordsCard(
                            myWords[randomWordNumber].translations[0],
                            words[(0 until myWords.size).random()],
                            words[(0 until myWords.size).random()],
                            words[(0 until myWords.size).random()],
                            myWords[randomWordNumber].textOrig,
                            0
                        )
                    )
                )
            }
        }
    }

    private fun initMyLists() {
        for (i in 0 until addedWordsList.size) {
            if (addedWordsList[i].translationsOfNoun != EmptyField) {
                myNouns.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfNoun)
                    )
                )
            }

            if (addedWordsList[i].translationsOfVerb != EmptyField) {
                myVerbs.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfVerb)
                    )
                )
            }

            if (addedWordsList[i].translationsOfAdverb != EmptyField) {
                myAdverbs.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdverb)
                    )
                )

            }

            if (addedWordsList[i].translationsOfAdjective != EmptyField) {
                myAdjectives.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdjective)
                    )
                )

            }
        }
    }


    @Inject
    lateinit var provideWordRepo: IWordRepo

    fun getAllWordsFromDb() {
        provideWordRepo.getAllWords().subscribeOn(Schedulers.io())
            .subscribe({ repos ->
                addedWordsList = repos
            }, {
                Log.d("Error: ", it.message!!)
            })
    }

    @Inject
    lateinit var app: App

    private fun readTxtFile(filename: String): String {
        var text = ""
        try {
            val sampleText: String =
                app
                    .assets
                    .open(filename)
                    .bufferedReader().use {
                        it.readText()
                    }
            text = sampleText
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return text + " "
    }

    private fun convertToList(text: String): List<String> {
        val list = arrayListOf<String>()

        val array = text.toCharArray()

        var word = ""

        for (i in 0 until array.size) {

            if (array[i] in 'а'..'я' || array[i] == 'й' || array[i] == 'ё') {
                word += array[i]
            } else {
                if (word != "") {
                    list.add(word)
                }
                word = ""
            }
        }
        return list
    }


}
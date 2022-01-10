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

        initMyLists()
        createTestCard()
    }

    fun createTestCard() {

        val random = partOfSpeechSet.random()

        println("partOfSpeechSet.random(): $random")

        if (myNouns.size > 0 || myVerbs.size > 0 || myAdverbs.size > 0 || myAdjectives.size > 0) {
            when (random) {
                "nouns" -> {
                    if (myNouns.size > 0) {
                        createWordRaw(myNouns, nounsList)
                    } else if (myVerbs.size > 0) {
                        createWordRaw(myVerbs, verbsList)
                    } else if (myAdverbs.size > 0) {
                        createWordRaw(myAdverbs, adverbsList)
                    } else if (myAdjectives.size > 0) {
                        createWordRaw(myAdjectives, adjectivesList)
                    }
                }
                "verbs" -> {
                    if (myVerbs.size > 0) {
                        createWordRaw(myVerbs, verbsList)
                    } else if (myNouns.size > 0) {
                        createWordRaw(myNouns, nounsList)
                    } else if (myAdverbs.size > 0) {
                        createWordRaw(myAdverbs, adverbsList)
                    } else if (myAdjectives.size > 0) {
                        createWordRaw(myAdjectives, adjectivesList)
                    }
                }
                "adverbs" -> {
                    if (myAdverbs.size > 0) {
                        createWordRaw(myAdverbs, adverbsList)
                    } else if (myNouns.size > 0) {
                        createWordRaw(myNouns, nounsList)
                    } else if (myVerbs.size > 0) {
                        createWordRaw(myVerbs, verbsList)
                    } else if (myAdjectives.size > 0) {
                        createWordRaw(myAdjectives, adjectivesList)
                    }
                }
                "adjectives" -> {
                    println("myAdjectives.size: ${myAdjectives.size}")
                    if (myAdjectives.size > 0) {
                        createWordRaw(myAdjectives, adjectivesList)
                    } else if (myNouns.size > 0) {
                        createWordRaw(myNouns, nounsList)
                    } else if (myVerbs.size > 0) {
                        createWordRaw(myVerbs, verbsList)
                    } else if (myAdverbs.size > 0) {
                        createWordRaw(myAdverbs, adverbsList)
                    }
                }
            }
        } else {
            noWordsForTests()
        }
    }

    private fun noWordsForTests() {
        // TODO
    }

    private fun createWordRaw(myWords: List<MyAddedWord>, words: List<String>) {

        val rightAnswerPosition = 0 // (0..3).random()
        val randomWordNumber = (0 until myWords.size).random()
        println("myWords.size: ${myWords.size}, randomWordNumber: $randomWordNumber, myWords[randomWordNumber].translations ${myWords[randomWordNumber].translations} ")

        when (rightAnswerPosition) {
            0 -> {
                liveDataCreateCardEvent.postValue(
                    Event(
                        WordsCard(
                            myWords[randomWordNumber].translations[0],
                            words[(0 until words.size).random()],
                            words[(0 until words.size).random()],
                            words[(0 until words.size).random()],
                            myWords[randomWordNumber].textOrig,
                            0
                        )
                    )
                )
            }
            1 -> {
                liveDataCreateCardEvent.postValue(
                    Event(
                        WordsCard(
                            words[(0 until words.size).random()],
                            myWords[randomWordNumber].translations[0],
                            words[(0 until words.size).random()],
                            words[(0 until words.size).random()],
                            myWords[randomWordNumber].textOrig,
                            1
                        )
                    )
                )
            }
            2 -> {
                liveDataCreateCardEvent.postValue(
                    Event(
                        WordsCard(
                            words[(0 until words.size).random()],
                            words[(0 until words.size).random()],
                            myWords[randomWordNumber].translations[0],
                            words[(0 until words.size).random()],
                            myWords[randomWordNumber].textOrig,
                            2
                        )
                    )
                )
            }
            3 -> {
                liveDataCreateCardEvent.postValue(
                    Event(
                        WordsCard(
                            words[(0 until words.size).random()],
                            words[(0 until words.size).random()],
                            words[(0 until words.size).random()],
                            myWords[randomWordNumber].translations[0],
                            myWords[randomWordNumber].textOrig,
                            3
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

                println("addedWordsList[$i].translationsOfAdjective: ${addedWordsList[i].translationsOfAdjective}")

                myAdjectives.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdjective)
                    )
                )
            }
        }


        println("myNouns test: ${myNouns}")
        println("myVerbs test: ${myVerbs}")
        println("myAdverbs test: ${myAdverbs}")
        println("myAdjectives test: ${myAdjectives}")


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
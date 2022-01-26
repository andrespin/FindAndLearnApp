package android.findandlearnapp.words_test

import android.findandlearnapp.App
import android.findandlearnapp.base.StringLanguage
import android.findandlearnapp.base.getLanguageOfWord
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.Event
import android.findandlearnapp.dictionary.repository.IWordRepo
import android.findandlearnapp.utils.EmptyField
import android.findandlearnapp.utils.convertToWordTranslationsList
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

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

    val liveDataSendNoWordsMessage = MutableLiveData<Event<String>>()

    private lateinit var nounsListRu: List<String>
    private lateinit var verbsListRu: List<String>
    private lateinit var adverbsListRu: List<String>
    private lateinit var adjectivesListRu: List<String>


    private var myNounsRu = mutableListOf<MyAddedWord>()
    private var myVerbsRu = mutableListOf<MyAddedWord>()
    private var myAdverbsRu = mutableListOf<MyAddedWord>()
    private var myAdjectivesRu = mutableListOf<MyAddedWord>()

    private var myNounsEn = mutableListOf<MyAddedWord>()
    private var myVerbsEn = mutableListOf<MyAddedWord>()
    private var myAdverbsEn = mutableListOf<MyAddedWord>()
    private var myAdjectivesEn = mutableListOf<MyAddedWord>()

    private var areWordsSplit = false

    private lateinit var addedWordsList: List<WordEntity>

    private val addedWordsListRu = mutableListOf<WordEntity>()

    private val addedWordsListEn = mutableListOf<WordEntity>()

    private val partOfSpeechSet = setOf("nouns", "verbs", "adverbs", "adjectives")
    private val partsOfSpeechList = listOf("nouns", "verbs", "adverbs", "adjectives")

    fun initLists() {
        nounsListRu = convertToList(readTxtFile("ru_nouns.txt"))
        verbsListRu = convertToList(readTxtFile("ru_verbs.txt"))
        adverbsListRu = convertToList(readTxtFile("ru_adverbs.txt"))
        adjectivesListRu = convertToList(readTxtFile("ru_adjectives.txt"))

        initMyLists()
        createTestCard()
    }

    private fun splitWordsAccordingToLang(list: List<WordEntity>) {
        for (i in 0 until list.size) {
            getLanguageOfWord(list[i].textOrig)
            when (getLanguageOfWord(list[i].textOrig)) {
                StringLanguage.English -> addedWordsListEn.add(list[i])
                StringLanguage.Russian -> addedWordsListRu.add(list[i])
            }
        }
    }

    fun createTestCard() {

        val random = partOfSpeechSet.random()

        println("partOfSpeechSet.random(): $random")

        if (myNounsRu.size > 0 || myVerbsRu.size > 0 || myAdverbsRu.size > 0 || myAdjectivesRu.size > 0) {
            when (random) {
                "nouns" -> {
                    if (myNounsRu.size > 0) {
                        createWordRaw(myNounsRu, nounsListRu)
                    } else if (myVerbsRu.size > 0) {
                        createWordRaw(myVerbsRu, verbsListRu)
                    } else if (myAdverbsRu.size > 0) {
                        createWordRaw(myAdverbsRu, adverbsListRu)
                    } else if (myAdjectivesRu.size > 0) {
                        createWordRaw(myAdjectivesRu, adjectivesListRu)
                    }
                }
                "verbs" -> {
                    if (myVerbsRu.size > 0) {
                        createWordRaw(myVerbsRu, verbsListRu)
                    } else if (myNounsRu.size > 0) {
                        createWordRaw(myNounsRu, nounsListRu)
                    } else if (myAdverbsRu.size > 0) {
                        createWordRaw(myAdverbsRu, adverbsListRu)
                    } else if (myAdjectivesRu.size > 0) {
                        createWordRaw(myAdjectivesRu, adjectivesListRu)
                    }
                }
                "adverbs" -> {
                    if (myAdverbsRu.size > 0) {
                        createWordRaw(myAdverbsRu, adverbsListRu)
                    } else if (myNounsRu.size > 0) {
                        createWordRaw(myNounsRu, nounsListRu)
                    } else if (myVerbsRu.size > 0) {
                        createWordRaw(myVerbsRu, verbsListRu)
                    } else if (myAdjectivesRu.size > 0) {
                        createWordRaw(myAdjectivesRu, adjectivesListRu)
                    }
                }
                "adjectives" -> {
                    println("myAdjectives.size: ${myAdjectivesRu.size}")
                    if (myAdjectivesRu.size > 0) {
                        createWordRaw(myAdjectivesRu, adjectivesListRu)
                    } else if (myNounsRu.size > 0) {
                        createWordRaw(myNounsRu, nounsListRu)
                    } else if (myVerbsRu.size > 0) {
                        createWordRaw(myVerbsRu, verbsListRu)
                    } else if (myAdverbsRu.size > 0) {
                        createWordRaw(myAdverbsRu, adverbsListRu)
                    }
                }
            }
        } else {
            noWordsForTests()
        }
    }

    private fun noWordsForTests() {
        liveDataSendNoWordsMessage.postValue(Event("Нет слов для отображения тестов"))
    }

    private fun createWordRaw(myWords: List<MyAddedWord>, words: List<String>) {

        val rightAnswerPosition = (0..3).random()
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
        initAllWords()
    }



    private fun initMyRussianWords() {
        for (i in 0 until addedWordsListRu.size) {
            if (addedWordsList[i].translationsOfNoun != EmptyField) {
                myNounsRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfNoun)
                    )
                )
            }
            if (addedWordsList[i].translationsOfVerb != EmptyField) {
                myVerbsRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfVerb)
                    )
                )
            }
            if (addedWordsList[i].translationsOfAdverb != EmptyField) {
                myAdverbsRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdverb)
                    )
                )

            }
            if (addedWordsList[i].translationsOfAdjective != EmptyField) {

                println("addedWordsList[$i].translationsOfAdjective: ${addedWordsList[i].translationsOfAdjective}")

                myAdjectivesRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdjective)
                    )
                )
            }
        }
    }

    private fun initMyEnglishWords() {
        for (i in 0 until addedWordsListEn.size) {
            if (addedWordsList[i].translationsOfNoun != EmptyField) {
                myNounsEn.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfNoun)
                    )
                )
            }

            if (addedWordsList[i].translationsOfVerb != EmptyField) {
                myVerbsEn.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfVerb)
                    )
                )
            }

            if (addedWordsList[i].translationsOfAdverb != EmptyField) {
                myAdverbsEn.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdverb)
                    )
                )
            }

            if (addedWordsList[i].translationsOfAdjective != EmptyField) {

                println("addedWordsList[$i].translationsOfAdjective: ${addedWordsList[i].translationsOfAdjective}")

                myAdjectivesRu.add(
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
                splitWordsAccordingToLang(addedWordsList)
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



    private fun initAllWords() {
        for (i in 0 until addedWordsList.size) {
            if (addedWordsList[i].translationsOfNoun != EmptyField) {
                myNounsRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfNoun)
                    )
                )
            }

            if (addedWordsList[i].translationsOfVerb != EmptyField) {
                myVerbsRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfVerb)
                    )
                )
            }

            if (addedWordsList[i].translationsOfAdverb != EmptyField) {
                myAdverbsRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdverb)
                    )
                )

            }

            if (addedWordsList[i].translationsOfAdjective != EmptyField) {

                println("addedWordsList[$i].translationsOfAdjective: ${addedWordsList[i].translationsOfAdjective}")

                myAdjectivesRu.add(
                    MyAddedWord(
                        addedWordsList[i].textOrig,
                        convertToWordTranslationsList(addedWordsList[i].translationsOfAdjective)
                    )
                )
            }
        }

        println("myNouns test: ${myNounsRu}")
        println("myVerbs test: ${myVerbsRu}")
        println("myAdverbs test: ${myAdverbsRu}")
        println("myAdjectives test: ${myAdjectivesRu}")
    }


}
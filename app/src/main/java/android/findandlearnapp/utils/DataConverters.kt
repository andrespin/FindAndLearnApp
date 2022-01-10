package android.findandlearnapp.utils

import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.dictionary.data.*
import android.findandlearnapp.words_manager.AddedWord
import android.util.Log
import java.lang.IndexOutOfBoundsException


fun convertToAddedWord(wordsEntity: List<WordEntity>): List<AddedWord> {
    val list = mutableListOf<AddedWord>()
    for (i in 0 until wordsEntity.size) {
        list.add(AddedWord(wordsEntity[i], addedWordIsNotChecked, false, i))
    }
    return list
}

fun convertToWordTranslations(listWordsTr: List<Tr>): List<WordTranslations> {
    val listWords = mutableListOf<WordTranslations>()
    for (i in 0 until listWordsTr.size) {
        listWords.add(
            WordTranslations(
                listWordsTr[i].pos,
                listWordsTr[i].text
            )
        )
    }
    return listWords
}

fun convertToWordTranslationsList(string: String): List<String> {
    var isOneWord = true
    val list = mutableListOf<String>()
    val char = string.toCharArray()
    var word = ""

    for (i in 0 until char.size) {
        if (char[i] != ',') {
            word += char[i]
        }
        if (char[i] == ',') {
            isOneWord = false
            word.filter { !it.isWhitespace() }
            list.add(word)
            word = ""
        }
    }
    if (isOneWord) {
        list.add(word)
    }
    return list
}

fun convertToWord(response: WordTranslationServerResponse): Word {
    var text = ""
    var ts = ""
    var isFound = true
    try {
        text = response.def[0]?.text ?: ""
        ts = response.def[0]?.ts ?: ""
    } catch (e: IndexOutOfBoundsException) {
        e.printStackTrace()
        isFound = false
    }
    return Word(
        text,
        ts,
        convertToWordDescription(response),
        isFound
    )
}

fun convertToWordDescription(response: WordTranslationServerResponse)
        : List<WordDescription> {
    val listWordDesc = mutableListOf<WordDescription>()

    for (i in 0 until response.def.size) {
        listWordDesc.add(
            WordDescription(
                response.def[i].pos,
                response.def[i].text,
                convertToTextWithWords(response.def[i].tr)
            )
        )
    }
    return listWordDesc
}

fun convertToWordDescription(addedWord: AddedWord): List<WordDescription> {
    val listWordDesc = mutableListOf<WordDescription>()

    if (addedWord.wordEntity.translationsOfNoun != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Noun",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfNoun
            )
        )
    }

    if (addedWord.wordEntity.translationsOfPronoun != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Pronoun",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfPronoun
            )
        )
    }
    if (addedWord.wordEntity.translationsOfAdjective != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Adjective",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfAdjective
            )
        )
    }
    if (addedWord.wordEntity.translationsOfVerb != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Verb",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfVerb
            )
        )
    }
    if (addedWord.wordEntity.translationsOfAdverb != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Adverb",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfAdverb
            )
        )
    }
    if (addedWord.wordEntity.translationsOfPreposition != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Preposition",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfPreposition
            )
        )
    }
    if (addedWord.wordEntity.translationsOfConjunction != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Conjunction",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfConjunction
            )
        )
    }
    if (addedWord.wordEntity.translationsOfInterjection != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Interjection",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfInterjection
            )
        )
    }
    if (addedWord.wordEntity.translationsOfNumeral != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Numeral",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfNumeral
            )
        )
    }
    if (addedWord.wordEntity.translationsOfParticle != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Particle",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfParticle
            )
        )
    }
    if (addedWord.wordEntity.translationsOfInvariable != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Invariable",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfInvariable
            )
        )
    }
    if (addedWord.wordEntity.translationsOfParticiple != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Participle",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfParticiple
            )
        )
    }
    if (addedWord.wordEntity.translationsOfAdverbialParticiple != EmptyField) {
        listWordDesc.add(
            WordDescription(
                "Adverbial participle",
                addedWord.wordEntity.textOrig,
                addedWord.wordEntity.translationsOfAdverbialParticiple
            )
        )
    }
    return listWordDesc
}

fun convertToTextWithWords(listWordsTr: List<Tr>): String {
    var text = ""
    for (i in 0 until listWordsTr.size) {
        text += if (i == 0) {
            listWordsTr[i].text
        } else {
            ", ${listWordsTr[i].text}"
        }
    }
    return text
}

private var translationsOfNoun = EmptyField
private var translationsOfPronoun = EmptyField
private var translationsOfAdjective = EmptyField

private var translationsOfVerb = EmptyField
private var translationsOfAdverb = EmptyField
private var translationsOfPreposition = EmptyField

private var translationsOfConjunction = EmptyField
private var translationsOfInterjection = EmptyField
private var translationsOfNumeral = EmptyField

private var translationsOfParticle = EmptyField
private var translationsOfInvariable = EmptyField
private var translationsOfParticiple = EmptyField

private var translationsOfAdverbialParticiple = EmptyField


fun convertToWordEntity(word: Word): WordEntity {
    associateTranslationsWithPartOfSpeechSections(word)
    val wordEntity = WordEntity(
        word.textOrig,
        word.txtPhonetics,
        translationsOfNoun,
        translationsOfPronoun,
        translationsOfAdjective,
        translationsOfVerb,
        translationsOfAdverb,
        translationsOfPreposition,
        translationsOfConjunction,
        translationsOfInterjection,
        translationsOfNumeral,
        translationsOfParticle,
        translationsOfInvariable,
        translationsOfParticiple,
        translationsOfAdverbialParticiple
    )
    assignEmptyFieldToTranslationVars()
    return wordEntity
}

fun assignEmptyFieldToTranslationVars() {
    translationsOfNoun = EmptyField
    translationsOfPronoun = EmptyField
    translationsOfAdjective = EmptyField

    translationsOfVerb = EmptyField
    translationsOfAdverb = EmptyField
    translationsOfPreposition = EmptyField

    translationsOfConjunction = EmptyField
    translationsOfInterjection = EmptyField
    translationsOfNumeral = EmptyField

    translationsOfParticle = EmptyField
    translationsOfInvariable = EmptyField
    translationsOfParticiple = EmptyField

    translationsOfAdverbialParticiple = EmptyField
}


private fun associateTranslationsWithPartOfSpeechSections(word: Word) {
    for (i in 0 until word.wordDescriptions.size) {
        when (word.wordDescriptions[i].partOfSpeech) {

            "noun" -> {
                translationsOfNoun = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "pronoun" -> {
                translationsOfPronoun = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "adjective" -> {
                translationsOfAdjective = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "verb" -> {
                translationsOfVerb = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "adverb" -> {
                translationsOfAdverb = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "preposition" -> {
                translationsOfPreposition = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "conjunction" -> {
                translationsOfConjunction = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "interjection" -> {
                translationsOfInterjection = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "numeral" -> {
                translationsOfNumeral = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "particle" -> {
                translationsOfParticle = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "invariable" -> {
                translationsOfInvariable = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "participle" -> {
                translationsOfParticiple = word.wordDescriptions[i].wordTranslationsOneLine
            }

            "adverbial participle" -> {
                translationsOfAdverbialParticiple = word.wordDescriptions[i].wordTranslationsOneLine
            }

            else -> {
                Log.d(
                    "word.wordDescriptions[i].partOfSpeech is ",
                    word.wordDescriptions[i].partOfSpeech
                )
                Log.d("partOfSpeech status", "Not found")
            }
        }
    }
}



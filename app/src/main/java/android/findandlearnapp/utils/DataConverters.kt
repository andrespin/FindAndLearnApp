package android.findandlearnapp.utils

import android.findandlearnapp.dictionary.data.*
import java.lang.IndexOutOfBoundsException

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

fun convertToWord(response: WordTranslationServerResponse): Word {
    var text = ""
    var ts = ""
    try {
        text = response.def[0].text
        ts = response.def[0].ts
    } catch (e: IndexOutOfBoundsException) {
        e.printStackTrace()
    }
    return Word(
        text,
        ts,
        convertToWordDescription(response)
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
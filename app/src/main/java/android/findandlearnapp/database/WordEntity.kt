package android.findandlearnapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "words_table")
data class WordEntity(

    @PrimaryKey
    var textOrig: String,

    var txtPhonetics: String,

    var translationsOfNoun: String,

    var translationsOfPronoun: String,

    var translationsOfAdjective: String,

    var translationsOfVerb: String,

    var translationsOfAdverb: String,

    var translationsOfPreposition: String,

    var translationsOfConjunction: String,

    var translationsOfInterjection: String,

    var translationsOfNumeral: String,

    var translationsOfParticle: String,

    var translationsOfInvariable: String,

    var translationsOfParticiple: String,

    var translationsOfAdverbialParticiple: String
)


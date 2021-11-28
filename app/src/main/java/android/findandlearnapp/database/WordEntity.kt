package android.findandlearnapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class WordEntity(

    @PrimaryKey
    var textOrig: String,

    var txtPhonetics: String

)



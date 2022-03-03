package android.findandlearnapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class, WordInListEntity::class], version = 3)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun wordInListDao(): WordInListDao
}


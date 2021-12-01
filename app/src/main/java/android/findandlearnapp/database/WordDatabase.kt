package android.findandlearnapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class], version = 2)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}

/*
        const val DB_NAME = "data.db"
        private var instance: Database1? = null
        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun getDatabase(context: Context?): Database1 {
            if (instance == null) {
                synchronized(Database1::class) {
                    instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        Database1::class.java,
                        "myDB"
                    ).build()
                }
            }
            return instance!!
        }
 */

/*
@Database(entities = [RxBook::class], version = 2)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
 */
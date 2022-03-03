package android.findandlearnapp.di

import android.content.Context
import android.findandlearnapp.App
import android.findandlearnapp.database.WordDao
import android.findandlearnapp.database.WordDatabase
import android.findandlearnapp.database.WordInListDao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun createWordsDatabase(app: App): WordDatabase {
        return Room.databaseBuilder(app, WordDatabase::class.java, "word_db10")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideWordDao(app: App): WordDao = createWordsDatabase(app).wordDao()

    @Provides
    @Singleton
    internal fun provideWordInListDao(app: App): WordInListDao =
        createWordsDatabase(app).wordInListDao()

}
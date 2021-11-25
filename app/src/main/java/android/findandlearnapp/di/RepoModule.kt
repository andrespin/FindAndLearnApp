package android.findandlearnapp.di

import android.findandlearnapp.dictionary.data.DictionaryApi
import android.findandlearnapp.dictionary.data.IWordTranslationRepo
import android.findandlearnapp.dictionary.data.RetrofitWordTranslationRepo
import android.findandlearnapp.utils.INetworkStatus
import dagger.Module
import dagger.Provides


@Module
class RepoModule {

    @Provides
    fun wordTranslationRepo(
        api: DictionaryApi,
        networkStatus: INetworkStatus
    ): IWordTranslationRepo =
        RetrofitWordTranslationRepo(api, networkStatus)

}
package android.findandlearnapp.di


import android.findandlearnapp.App
import android.findandlearnapp.dictionary.data.DictionaryApi
import android.findandlearnapp.utils.AndroidNetworkStatus
import android.findandlearnapp.utils.INetworkStatus
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Named("baseUrl")
    @Provides
    fun baseUrl(): String = "https://dictionary.yandex.net/api/v1/"

    @Provides
    @Singleton
    fun api(@Named("baseUrl") baseUrl: String, gson: Gson): DictionaryApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(DictionaryApi::class.java)

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    @Provides
    @Singleton
    fun networkStatus(app: App): INetworkStatus = AndroidNetworkStatus(app)

}
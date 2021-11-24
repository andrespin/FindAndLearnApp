package android.findandlearnapp.dictionary.data

sealed class DictionaryAndCardsAppState {

    data class Success(val data: List<WordDescription>) : DictionaryAndCardsAppState()
    data class Error(val error: Throwable) : DictionaryAndCardsAppState()
    data class Loading(val progress: Int?) : DictionaryAndCardsAppState()

}

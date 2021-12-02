package android.findandlearnapp.dictionary.data

import android.findandlearnapp.database.WordEntity

sealed class AppState {

    data class Success(val data: Word) : AppState()
    data class SuccessDb(val data: WordEntity) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()

}

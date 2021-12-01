package android.findandlearnapp.base

import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.utils.convertToWord
import android.findandlearnapp.utils.word_translation_from_Russian_to_English
import android.findandlearnapp.utils.yandexDictionaryKey
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val wordAdded = true

    protected val wordNotAdded = false

    abstract fun handleError(error: Throwable)

}
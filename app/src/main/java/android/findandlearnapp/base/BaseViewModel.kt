package android.findandlearnapp.base

import android.findandlearnapp.dictionary.data.AppState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    val wordAdded = true

    val wordNotAdded = false

    abstract fun handleError(error: Throwable)

    protected fun doSmth() {
        println("smth")
    }

}
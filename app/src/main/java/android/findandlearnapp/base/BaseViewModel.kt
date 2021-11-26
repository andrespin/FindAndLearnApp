package android.findandlearnapp.base

import android.findandlearnapp.dictionary.data.AppState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel(){

    abstract fun handleError(error: Throwable)

}
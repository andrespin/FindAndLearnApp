package android.findandlearnapp.base

import android.findandlearnapp.dictionary.data.DictionaryAndCardsAppState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : DictionaryAndCardsAppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel(){

    abstract fun handleError(error: Throwable)

}
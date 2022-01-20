package android.findandlearnapp.words_app_settings

import android.findandlearnapp.dictionary.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutAppViewModel : ViewModel() {

    val liveDataEventApiVisible = MutableLiveData<Event<Boolean>>()

    val liveDataEventApiInvisible = MutableLiveData<Event<Boolean>>()

    val liveDataEventAboutAppVisible = MutableLiveData<Event<Boolean>>()

    val liveDataEventAboutAppInvisible = MutableLiveData<Event<Boolean>>()

    fun aboutAppClicked(isChecked: Boolean) =
            when(!isChecked) {
                true -> liveDataEventAboutAppVisible.value = Event(true)
                false -> liveDataEventAboutAppInvisible.value = Event(true)
            }

    fun apiClicked(isChecked: Boolean) =
        when(!isChecked){
            true -> liveDataEventApiVisible.value = Event(true)
            false -> liveDataEventApiInvisible.value = Event(true)
        }
}
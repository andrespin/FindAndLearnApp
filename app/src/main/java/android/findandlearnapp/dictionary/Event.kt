package android.findandlearnapp.dictionary

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun getContent(): T = content
}
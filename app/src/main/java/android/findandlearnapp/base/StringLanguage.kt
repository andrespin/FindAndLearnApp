package android.findandlearnapp.base

enum class StringLanguage {
    Russian, English, NotIdentified
}

fun containsLetters(str: String): Boolean {

    val array = str.toCharArray()

    val english = mutableListOf<Char>()
    english += 'A'..'Z'
    english += 'a'..'z'

    val russian = mutableListOf<Char>()
    russian += 'А'..'Я'
    russian += 'а'..'я'

    for (i in array.indices) {
        if (array[i] in english || array[i] in russian) {
            return true
        }
    }
    return false
}

fun getLanguageOfWord(string: String): StringLanguage {

    val map = mutableMapOf("isRussian" to false, "isEnglish" to false, "NotIdentified" to false)

    val text = string.toCharArray()

    text.forEach {
        for (letter in string) {
            if (letter in 'A'..'Z' || letter in 'a'..'z') {
                map["isEnglish"] = true
            }
            if (letter in 'А'..'Я' || letter in 'а'..'я') {
                map["isRussian"] = true
            }
//            if (letter !in 'А'..'Я' && letter !in 'а'..'я' && letter !in 'A'..'Z' && letter !in 'a'..'z') {
//                map["NotIdentified"] = true
//            }
        }
    }
    if (map["isRussian"]!! && map["isEnglish"]!! || map["NotIdentified"]!!) return StringLanguage.NotIdentified
    if (map["isRussian"]!!) return StringLanguage.Russian
    if (map["isEnglish"]!!) return StringLanguage.English
    return StringLanguage.NotIdentified
}




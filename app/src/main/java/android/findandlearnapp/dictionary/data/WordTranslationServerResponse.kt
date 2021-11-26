package android.findandlearnapp.dictionary.data

data class WordTranslationServerResponse(
    val def: List<Def>,
    val head: Head
)

data class Word(
    val textOrig: String,
    val txtPhonetics: String,
    val wordDescriptions: List<WordDescription>
)

data class WordDescription(
    val partOfSpeech: String,
    val textOrig: String,
    val wordTranslationsOneLine: String
)

data class WordTranslations(
    val partOfSpeech: String,
    val textTranslatedWord: String
)

data class Def(
    // часть речи
    val pos: String,
    // Слова в оригинале
    val text: String,
    val tr: List<Tr>,
    // транскрипция
    val ts: String
)

class Head

data class Tr(
    val asp: String,
    val ex: List<Ex>,
    val fr: Int,
    val gen: String,
    val mean: List<Mean>,
    val num: String,
    // часть речи
    val pos: String,
    val syn: List<Syn>,
    // перевод слова
    val text: String
)

data class Ex(
    val text: String,
    val tr: List<TrX>
)

data class Mean(
    val text: String
)

data class Syn(
    val fr: Int,
    val gen: String,
    val pos: String,
    val text: String
)

data class TrX(
    val text: String
)
package android.findandlearnapp.dictionary.adapter

import android.findandlearnapp.databinding.DictionaryFragmentRecyclerviewItemBinding
import android.findandlearnapp.dictionary.data.WordDescription
import androidx.recyclerview.widget.RecyclerView

class DictionaryViewHolder(private val vb: DictionaryFragmentRecyclerviewItemBinding) :
    RecyclerView.ViewHolder(vb.root) {

    fun bind(response: WordDescription) {
        vb.txtItemPartOfSpeech.text = response.partOfSpeech
        var text = ""
        for (i in 0 until response.wordTranslations.size) {
            text += if (i == 0) {
                response.wordTranslations[i].textTranslatedWord
            } else {
                ", ${response.wordTranslations[i].textTranslatedWord}"
            }
        }
        vb.txtItemWordTranslation.text = text
    }
}
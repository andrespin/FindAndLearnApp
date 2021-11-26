package android.findandlearnapp.dictionary.adapter

import android.findandlearnapp.databinding.DictionaryFragmentRecyclerviewItemBinding
import android.findandlearnapp.dictionary.data.WordDescription
import androidx.recyclerview.widget.RecyclerView

class DictionaryViewHolder(private val vb: DictionaryFragmentRecyclerviewItemBinding) :
    RecyclerView.ViewHolder(vb.root) {

    fun bind(response: WordDescription) {
        vb.txtItemPartOfSpeech.text = response.partOfSpeech
        vb.txtItemWordTranslation.text = response.wordTranslationsOneLine

    }
}
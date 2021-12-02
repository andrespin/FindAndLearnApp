package android.findandlearnapp.words_manager.adapter

import android.findandlearnapp.databinding.ItemRecyclerviewFragmentWordsmanagerBinding
import androidx.recyclerview.widget.RecyclerView

class WordsManagerViewHolder
    (private val vb: ItemRecyclerviewFragmentWordsmanagerBinding) :
    RecyclerView.ViewHolder(vb.root) {

    fun bind(addedWord: String) {
        vb.txtAddedWord.text = addedWord
    }

}



package android.findandlearnapp.words_manager.adapter

import android.content.Context
import android.findandlearnapp.databinding.ItemRecyclerviewFragmentWordsmanagerBinding
import android.findandlearnapp.words_manager.AddedWord
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class WordsManagerViewHolder
    (private val vb: ItemRecyclerviewFragmentWordsmanagerBinding) :
    RecyclerView.ViewHolder(vb.root) {

    fun bind(AddedWord: AddedWord, fragmentContext: Context) {
        vb.txtAddedWord.text = AddedWord.wordEntity.textOrig
        vb.txtAddedWord.background = AppCompatResources.getDrawable(
            fragmentContext,
            AddedWord.background
        )
    }

}



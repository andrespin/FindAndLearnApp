package android.findandlearnapp.dialogs.adapter

import android.findandlearnapp.databinding.RecyclerviewWordListsItemBinding
import androidx.recyclerview.widget.RecyclerView

class WordListsViewHolder(private val vb: RecyclerviewWordListsItemBinding) : RecyclerView.ViewHolder(vb.root){

    fun bind(list : WordList) {
        vb.txtListName.text = list.name.replace("listName","")
        vb.txtWordsNumber.text = list.numberOfWords.toString()
    }

}
package android.findandlearnapp.words_manager.mylists.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.findandlearnapp.R
import android.findandlearnapp.databinding.RecyclerviewWordListsItemBinding
import android.findandlearnapp.dialogs.adapter.WordList
import androidx.recyclerview.widget.RecyclerView

class MyListsViewHolder(
    private val vb: RecyclerviewWordListsItemBinding,
    private val fragmentContext: Context
) : RecyclerView.ViewHolder(vb.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(list: WordList, pos: Int) {
        vb.txtListName.text = list.name.replace("listName","")
        vb.txtWordsNumber.text = list.numberOfWords.toString()
        vb.myListsLayout.background = fragmentContext.getDrawable(list.background!!)

        if (!list.isLongClicked!!) {
            when (pos % 2 == 0) {
                false -> vb.myListsLayout.background =
                    fragmentContext.getDrawable(R.drawable.back_my_lists)
            }
        }
    }

}


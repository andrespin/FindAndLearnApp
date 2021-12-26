package android.findandlearnapp.words_manager.adapter

import android.content.Context
import android.findandlearnapp.databinding.ItemRecyclerviewFragmentWordsmanagerBinding
import android.findandlearnapp.words_manager.WordsManagerViewModel
import android.findandlearnapp.words_manager.AddedWord
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WordsManagerAdapter(val viewModel: WordsManagerViewModel, val fragmentContext: Context) :
    RecyclerView.Adapter<WordsManagerViewHolder>() {

    private var data: MutableList<AddedWord> = mutableListOf()

    fun setData(data: MutableList<AddedWord>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun updateWordData(addedWord: AddedWord) {
        Log.d("adapter update status", "works")
        this.data[addedWord.position] = addedWord
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsManagerViewHolder =
        WordsManagerViewHolder(
            ItemRecyclerviewFragmentWordsmanagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                println("this.layoutPosition ${this.layoutPosition}")
                viewModel.itemViewOnClickListener(data[this.layoutPosition])
            }

            itemView.setOnLongClickListener {
                println("this.layoutPosition ${this.layoutPosition}")
                viewModel.itemViewOnLongClickListener(data[this.layoutPosition])
                true
            }
        }


    override fun onBindViewHolder(holder: WordsManagerViewHolder, position: Int) {
        holder.bind(data[position], fragmentContext)
    }

    override fun getItemCount() = data.size

}


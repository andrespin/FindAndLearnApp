package android.findandlearnapp.dialogs.adapter

import android.content.Context
import android.findandlearnapp.databinding.RecyclerviewWordListsItemBinding
import android.findandlearnapp.dialogs.ListsDialogViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

data class WordList(
    val name: String,
    val numberOfWords: Int,
    var isLongClicked: Boolean? = null,
    var position: Int? = null,
    var background: Int? = null
)

class WordListsAdapter(
    private val viewModel: ListsDialogViewModel
) :
    RecyclerView.Adapter<WordListsViewHolder>() {

    private var data = mutableListOf<WordList>()

    fun setData(data: MutableList<WordList>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addNewWordList(wordList: WordList) {
        println("data.size before: ${data.size}")
        data.add(wordList)
        println("data.size after: ${data.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListsViewHolder =
        WordListsViewHolder(
            RecyclerviewWordListsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                viewModel.itemViewOnClickListener(data[this.layoutPosition])
            }
        }

    override fun onBindViewHolder(holder: WordListsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}
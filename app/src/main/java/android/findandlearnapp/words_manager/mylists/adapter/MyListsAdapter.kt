package android.findandlearnapp.words_manager.mylists.adapter

import android.content.Context
import android.findandlearnapp.databinding.RecyclerviewWordListsItemBinding
import android.findandlearnapp.dialogs.adapter.WordList
import android.findandlearnapp.words_manager.mylists.MyListsViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyListsAdapter(private val viewModel: MyListsViewModel, private val fragmentContext: Context) :
    RecyclerView.Adapter<MyListsViewHolder>(){

    private var data = mutableListOf<WordList>()

    fun setData(data: MutableList<WordList>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addNewWordList(wordList: WordList) {
        data[wordList.position!!] = wordList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListsViewHolder =
        MyListsViewHolder(
            RecyclerviewWordListsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            fragmentContext
        ).apply {
            itemView.setOnClickListener {
                viewModel.itemViewClickListener(data[this.layoutPosition])
            }
            itemView.setOnLongClickListener {
                println("this.layoutPosition ${this.layoutPosition}")
                println("data.size ${data.size}")
                viewModel.itemViewOnLongClickListener(data[this.layoutPosition])
                true
            }
        }

    override fun onBindViewHolder(holder: MyListsViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size

}

/*
class WordListsAdapter(private val viewModel: ListsDialogViewModel) :
    RecyclerView.Adapter<WordListsViewHolder>()
{

    private var data = mutableListOf<WordsList>()

    fun setData(data: MutableList<WordsList>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addNewWordList(wordsList: WordsList) {
        data.add(wordsList)
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
 */
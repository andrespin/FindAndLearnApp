package android.findandlearnapp.words_manager.adapter

import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.databinding.ItemRecyclerviewFragmentWordsmanagerBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WordsManagerAdapter : RecyclerView.Adapter<WordsManagerViewHolder>() {

    private var data: List<WordEntity> = arrayListOf()

    fun setData(data: List<WordEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsManagerViewHolder {
        return WordsManagerViewHolder(
            ItemRecyclerviewFragmentWordsmanagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordsManagerViewHolder, position: Int) {
        holder.bind(data[position].textOrig)
    }

    override fun getItemCount() = data.size

}


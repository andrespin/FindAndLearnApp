package android.findandlearnapp.words_manager.adapter

import android.findandlearnapp.databinding.DictionaryFragmentRecyclerviewItemBinding
import android.findandlearnapp.dictionary.adapter.DictionaryViewHolder
import android.findandlearnapp.dictionary.data.WordDescription
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WordAdapter : RecyclerView.Adapter<WordViewHolder>(){

    private var data: List<WordDescription> = arrayListOf()

    fun setData(data: List<WordDescription>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            DictionaryFragmentRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(data[position])
    }

}
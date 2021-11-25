package android.findandlearnapp.dictionary.adapter


import android.findandlearnapp.databinding.DictionaryFragmentRecyclerviewItemBinding
import android.findandlearnapp.dictionary.data.WordDescription
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DictionaryAdapter : RecyclerView.Adapter<DictionaryViewHolder>() {

    private var data: List<WordDescription> = arrayListOf()

    fun setData(data: List<WordDescription>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder =
        DictionaryViewHolder(
            DictionaryFragmentRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}
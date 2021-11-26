package android.findandlearnapp.dictionary

import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentDictionaryBinding
import android.findandlearnapp.dictionary.adapter.DictionaryAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding

    private lateinit var viewModel: DictionaryViewModel

    private val adapter: DictionaryAdapter by lazy { DictionaryAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDictionaryBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DictionaryViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }

        binding.searchViewFindWord.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.translateTheWord(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("onQueryTextChange")
                return false
            }
        })


    }



    companion object {

        fun newInstance() =
            DictionaryFragment()
    }
}
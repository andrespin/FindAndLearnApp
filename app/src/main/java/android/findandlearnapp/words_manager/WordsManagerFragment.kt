package android.findandlearnapp.words_manager

import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.database.WordEntity
import android.findandlearnapp.databinding.FragmentDictionaryBinding
import android.findandlearnapp.databinding.FragmentWordsManagerBinding
import android.findandlearnapp.dictionary.DictionaryViewModel
import android.findandlearnapp.dictionary.adapter.DictionaryAdapter
import android.findandlearnapp.words_manager.adapter.WordsManagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


class WordsManagerFragment : Fragment() {

    private lateinit var binding: FragmentWordsManagerBinding

    private lateinit var viewModel: WordsManagerViewModel

    private val adapter: WordsManagerAdapter by lazy { WordsManagerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentWordsManagerBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel = ViewModelProvider(this).get(WordsManagerViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
        viewModel.getAllWordsFromDb()
        viewModel.liveDataWords.observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(wordEntity: List<WordEntity>) {
        adapter.setData(wordEntity)
    }

    private fun initViews() {
        binding.rvListOfAddedWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListOfAddedWords.adapter = adapter
    }
}
package android.findandlearnapp.words_manager.en_words

import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentEngWordsBinding
import android.findandlearnapp.databinding.FragmentWordsManagerBinding
import android.findandlearnapp.words_manager.AddedWord
import android.findandlearnapp.words_manager.LanguageOfWords
import android.findandlearnapp.words_manager.WordsManagerViewModel
import android.findandlearnapp.words_manager.adapter.WordsManagerAdapter
import android.findandlearnapp.words_manager.addedWordInfo
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class EngWordsFragment : Fragment() {

    private lateinit var binding: FragmentEngWordsBinding

    private lateinit var viewModel: WordsManagerViewModel

    private val adapter: WordsManagerAdapter by lazy {
        WordsManagerAdapter(
            viewModel,
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentEngWordsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
        displayData()
        initLiveDataCheckWord()
        initButtons()
    }

    private fun initLiveDataCheckWord() {
        viewModel.liveDataCheckWord.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                Log.d("event status", "works")
                viewModel.setAddedWord(it)
                adapter.updateWordData(it)
            }
        })

        viewModel.liveDataButtonsVisibility.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                binding.btnCancelChecked.visibility = it.visibility
                binding.btnDeleteChecked.visibility = it.visibility
            }
        })

        viewModel.liveDataCheckedWords.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                adapter.setData(it as MutableList<AddedWord>)
            }
        })

        viewModel.liveDataNavigationEvent.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                val bundle = bundleOf(addedWordInfo to it)
                findNavController().navigate(R.id.action_en_words_to_word, bundle)
            }
        })
    }

    private fun initViews() {
        binding.rvListOfAddedWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListOfAddedWords.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(WordsManagerViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private fun initButtons() {
        binding.btnDeleteChecked.setOnClickListener {
            viewModel.deleteAddedWords()
        }

        binding.btnCancelChecked.setOnClickListener {
            viewModel.setAddedWordsUnchecked()
        }
    }

    private fun displayData() {
        viewModel.getAllWordsFromDb(LanguageOfWords.English)
        viewModel.liveDataWords.observe(
            viewLifecycleOwner,
            {
                adapter.setData(it as MutableList<AddedWord>)
            })
    }

}
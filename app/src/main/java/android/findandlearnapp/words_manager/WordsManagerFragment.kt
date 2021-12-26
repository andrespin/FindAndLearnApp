package android.findandlearnapp.words_manager

import android.findandlearnapp.App
import android.findandlearnapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.databinding.FragmentWordsManagerBinding
import android.findandlearnapp.words_manager.adapter.WordsManagerAdapter
import android.os.Parcelable
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.navigation.fragment.findNavController

const val addedWordInfo = "word"

class WordsManagerFragment : Fragment() {

    private lateinit var binding: FragmentWordsManagerBinding

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
        return FragmentWordsManagerBinding.inflate(inflater, container, false).also {
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
                findNavController().navigate(R.id.action_to_word, bundle)
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
        viewModel.getAllWordsFromDb()
        viewModel.liveDataWords.observe(
            viewLifecycleOwner,
            {
                adapter.setData(it as MutableList<AddedWord>)
            })
    }

}
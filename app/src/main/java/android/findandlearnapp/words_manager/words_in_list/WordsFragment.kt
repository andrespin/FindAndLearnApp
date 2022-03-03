package android.findandlearnapp.words_manager.words_in_list

import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentWordsBinding
import android.findandlearnapp.words_manager.AddedWord
import android.findandlearnapp.words_manager.WordsManagerViewModel
import android.findandlearnapp.words_manager.adapter.WordsManagerAdapter
import android.findandlearnapp.words_manager.addedWordInfo
import android.findandlearnapp.words_manager.en_words.checkedWords
import android.findandlearnapp.words_manager.mylists.list_Name
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager


class WordsFragment : Fragment() {

    private lateinit var binding: FragmentWordsBinding

    private lateinit var viewModel: WordsManagerViewModel

    private lateinit var listName: String

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
        return FragmentWordsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   println("arguments?.getString(listName) ${arguments?.getString(list_Name)}")
        listName = arguments?.getString(list_Name)!!
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
                binding.layoutWithButtons.visibility = it.visibility
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
                findNavController().navigate(R.id.action_myWordsInList_to_word, bundle)
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
            viewModel.deleteWordsInCurrentList()
        }

        binding.btnCancelChecked.setOnClickListener {
            viewModel.setAddedWordsUnchecked()
        }

        binding.btnAddToList.setOnClickListener {
            println("viewModel.getCheckedWords(true): ${viewModel.getCheckedWords(true)}")
            val bundle = bundleOf(checkedWords to viewModel.getCheckedWords(true))
            it.findNavController().navigate(
                R.id.action_en_words_to_AddingToListDialogFragment,
                bundle
            )
        }
    }

    private fun displayData() {
        viewModel.getWordsInCurrentList(listName)
        viewModel.getWordsInList.observe(
            viewLifecycleOwner,
            {
                adapter.setData(it as MutableList<AddedWord>)
            })
    }

}
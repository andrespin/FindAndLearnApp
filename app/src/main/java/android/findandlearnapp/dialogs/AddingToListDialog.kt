package android.findandlearnapp.dialogs

import android.findandlearnapp.App
import android.findandlearnapp.R
import android.findandlearnapp.databinding.DialogAddingBinding
import android.findandlearnapp.dialogs.adapter.WordListsAdapter
import android.findandlearnapp.dialogs.adapter.WordList
import android.findandlearnapp.words_manager.AddedWord
import android.findandlearnapp.words_manager.en_words.checkedWords
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

data class ListWhereToAdd(val list: WordList)

const val keyWordsList = "keyWordsList"

class AddingToListDialog : DialogFragment() {

    private lateinit var viewModel: ListsDialogViewModel

    private lateinit var binding: DialogAddingBinding

    private lateinit var checkedAddedWordsList: List<AddedWord>

    private val adapter: WordListsAdapter by lazy {
        WordListsAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView")
        return DialogAddingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        setupClickListeners()
        viewModel.getAllWordsInList()
        val value = arguments?.getStringArray(checkedWords)
        viewModel.initWordsToSave(value)
        println("onViewCreated")
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner
        ) {
            adapter.addNewWordList(
                WordList(
                    it,
                    0
                )
            )
        }

    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    private fun initAdapter() {
        with(binding) {
            rvWordsLists.layoutManager = LinearLayoutManager(requireContext())
            rvWordsLists.adapter = adapter
        }
    }

    private fun setupClickListeners() {

        viewModel.livaDataLists.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        viewModel.eventSelectListToSave.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                openSaveOrNotAlertDialog(it)
            }
        })

        viewModel.eventUpdateAdapter.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                adapter.addNewWordList(it)
                println("added new list ${it.name}")
            }
        })

        with(binding) {
            imgAddNewList.setOnClickListener {
                findNavController().navigate(R.id.action_AddingToListDialogFragment_to_CreatingNewListDialogFragment)
            }

            // https://stackoverflow.com/questions/51326437/navigation-component-popbackstack-with-arguments
            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun openSaveOrNotAlertDialog(list: WordList) {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Сохранить слова в этом ${list.name} списке?")
            .setNeutralButton(resources.getString(R.string.ru_cancel)) { dialog, which ->

            }
            .setPositiveButton(resources.getString(R.string.ru_ok)) { dialog, which ->
                viewModel.saveWordsInList(list)
                findNavController().popBackStack()
            }
            .show()

    }

    override fun onStart() {
        super.onStart()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ListsDialogViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
    }

}
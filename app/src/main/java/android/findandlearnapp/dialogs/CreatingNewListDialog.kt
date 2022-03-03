package android.findandlearnapp.dialogs

import android.findandlearnapp.App
import android.findandlearnapp.R
import android.findandlearnapp.base.containsLetters
import android.findandlearnapp.database.WordInListEntity
import android.findandlearnapp.databinding.DialogCreatingNewListBinding
import android.findandlearnapp.dialogs.adapter.WordListsAdapter
import android.findandlearnapp.dialogs.adapter.WordList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreatingNewListDialog : DialogFragment() {

    private lateinit var viewModel: ListsDialogViewModel

    private lateinit var binding: DialogCreatingNewListBinding

    private val adapter: WordListsAdapter by lazy {
        WordListsAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DialogCreatingNewListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupClickListeners()
    }

    private fun setupClickListeners() {

        with(binding) {
            btnCancel.setOnClickListener {
                findNavController().popBackStack()
                Log.d("Cancel", "Pressed")
            }
            btnCreateList.setOnClickListener {
                val listName = "listName" + editTxtEnter.text.toString()
                Log.d("listName", listName)
                if (containsLetters(listName)) {
                    val wordsList = WordList(listName, 0)
                    viewModel.createNewList(wordsList)
                    viewModel.addListToAdapter(wordsList)
                    //  findNavController().popBackStack()

                    val navController = findNavController()
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "key",
                        wordsList.name
                    )
                    navController.popBackStack()
                    /*
                    navController.previousBackStackEntry?.savedStateHandle?.set("key", result)
navController.popBackStack()
                     */


                } else {
                    showEnterNameAlertDialog()
                }
            }
        }
    }

    private fun setDataToAdapter(list: List<WordInListEntity>) {

    }

    private fun initListeners() {
        viewModel.liveDataWordLists.observe(viewLifecycleOwner, {
            setDataToAdapter(it)
        })

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ListsDialogViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
    }


    private fun showEnterNameAlertDialog() =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Внимание!")
            .setMessage("Укажите название создавемого вами списка слов")
            .setIcon(R.drawable.ic_attention_triangle)
            .setPositiveButton(resources.getString(R.string.ru_ok)) { dialog, which ->
            }
            .show()

}
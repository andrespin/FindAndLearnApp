package android.findandlearnapp.words_manager.mylists

import android.findandlearnapp.App
import android.findandlearnapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.databinding.FragmentMyListsBinding
import android.findandlearnapp.words_manager.mylists.adapter.MyListsAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

const val list_Name = "listName"

class MyListsFragment : Fragment() {

    private lateinit var binding: FragmentMyListsBinding

    private lateinit var viewModel: MyListsViewModel

    private val adapter: MyListsAdapter by lazy {
        MyListsAdapter(
            viewModel,
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMyListsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        initListeners()
    }


    private fun initListeners() {

        with(viewModel) {
            getAllWordsInList()
            eventNavigateToList.observe(viewLifecycleOwner, { event ->
                event?.getContentIfNotHandledOrReturnNull()?.let {
                    val bundle = bundleOf(list_Name to it)
                    findNavController().navigate(
                        R.id.action_myWordLists_to_myWordsInList,
                        bundle
                    )
                }
            })

            livaDataLists.observe(viewLifecycleOwner, {
                adapter.setData(it)
            })

            eventCheckList.observe(viewLifecycleOwner, { event ->
                event?.getContentIfNotHandledOrReturnNull()?.let {
                    adapter.addNewWordList(it)
                }
            })

            eventSwitchButtonsVisibility.observe(viewLifecycleOwner, { event ->
                event?.getContentIfNotHandledOrReturnNull()?.let {
                    with(binding) {
                        layoutButtons.visibility = it.visibility
                    }
                }
            })

            with(binding) {
                btnCancelChecked.setOnClickListener {
                    setAddedListsUnchecked()
                }

                btnDeleteChecked.setOnClickListener {
                    deleteCheckedLists()
                }
            }

        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MyListsViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private fun initAdapter() {
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = adapter
    }


}
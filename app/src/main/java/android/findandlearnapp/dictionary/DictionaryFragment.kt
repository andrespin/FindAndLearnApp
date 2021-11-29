package android.findandlearnapp.dictionary

import android.findandlearnapp.App
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.base.BaseFragment
import android.findandlearnapp.database.WordDao
import android.findandlearnapp.database.WordDatabase
import android.findandlearnapp.databinding.FragmentDictionaryBinding
import android.findandlearnapp.dictionary.adapter.DictionaryAdapter
import android.findandlearnapp.dictionary.data.AppState
import android.findandlearnapp.dictionary.data.Word
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DictionaryFragment : BaseFragment() {

    private lateinit var binding: FragmentDictionaryBinding

    private lateinit var viewModel: DictionaryViewModel

    private lateinit var word: Word

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
        initViews()
        viewModel = ViewModelProvider(this).get(DictionaryViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
        viewModel.liveDataForViewToObserve.observe(viewLifecycleOwner, { renderData(it) })


        viewModel.liveDataImgPutWordToDb.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                binding.imgPutWordToDb.visibility = it.visibility
                binding.imgPutWordToDb.setImageResource(it.source)
                println(it)
            }
        })
        initListeners()
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                val data = appState.data
                word = data
                binding.txtWord.text = data.textOrig
                binding.txtPhonetics.text = data.txtPhonetics
                adapter.setData(data.wordDescriptions)
            }

            is AppState.Loading -> {
                showViewLoading()
            }

            is AppState.Error -> {
                showViewWorking()
                showAlertDialog("Error", appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
        binding.progressBarRound.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.progressBarRound.visibility = View.VISIBLE
    }

    private fun initViews() {
        binding.rvTranslation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTranslation.adapter = adapter
    }

    private fun initListeners() {
        binding.searchViewFindWord.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.translateTheWord(query ?: "")
                Log.d("SearchView status", "onQueryTextSubmit")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchView status", "onQueryTextChanged")
                return false
            }
        })

        binding.imgPutWordToDb.setOnClickListener {
            viewModel.addWordToDb(word)

        }

    }

    companion object {

        fun newInstance() =
            DictionaryFragment()
    }
}
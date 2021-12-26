package android.findandlearnapp.words_manager

import android.annotation.SuppressLint
import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentWordBinding
import android.findandlearnapp.databinding.FragmentWordsManagerBinding
import android.findandlearnapp.dictionary.DictionaryViewModel
import android.findandlearnapp.utils.convertToWordDescription
import android.findandlearnapp.words_manager.adapter.WordAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager


class WordFragment : Fragment() {

    private lateinit var binding: FragmentWordBinding

    private lateinit var viewModel: WordViewModel

    private val adapter: WordAdapter by lazy { WordAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentWordBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setData()
    }

    private fun setData() {
        val word = arguments?.getParcelable<AddedWord>(addedWordInfo)
        binding.rvTranslation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTranslation.adapter = adapter
        binding.txtPhonetics.text = word!!.wordEntity.txtPhonetics
        binding.txtWord.text = word!!.wordEntity.textOrig
        adapter.setData(convertToWordDescription(word!!))
        println("convertToWordDescription(word!!) ${convertToWordDescription(word!!)}")
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(WordViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
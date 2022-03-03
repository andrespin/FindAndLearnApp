package android.findandlearnapp.words_test

import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentResultBinding
import android.findandlearnapp.words_manager.AddedWord
import android.findandlearnapp.words_manager.addedWordInfo
import androidx.lifecycle.ViewModelProvider


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private lateinit var viewModel: WordTestViewModel

    private lateinit var testResult: TestResultData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentResultBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WordTestViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
        testResult = arguments?.getParcelable<TestResultData>(resultTestInfo)!!

    }

    private fun initListeners() {
        val areAllAnswersCorrect = arguments?.getBoolean(resultTestInfo)
        with(binding) {
            
        }
    }

}
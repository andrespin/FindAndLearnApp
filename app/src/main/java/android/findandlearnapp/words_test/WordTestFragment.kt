package android.findandlearnapp.words_test

import android.annotation.SuppressLint
import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentWordTestBinding
import android.findandlearnapp.words_manager.addedWordInfo
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

const val resultTestInfo = "resultTestInfo"

class WordTestFragment : Fragment() {

    private lateinit var binding: FragmentWordTestBinding

    private lateinit var viewModel: TestViewModel

    private var selectedPosition: Int? = null

    private var rightPosition: Int? = null

    private var areAllAnswersCorrect = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentWordTestBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }
        initObservers()
        initListeners()
        startTest()
    }

    private fun startTest() {
        viewModel.initLists()
    }

    private fun initListeners() {
        with(binding) {
            imgGetResult.setOnClickListener {
                val totalAnswers = viewModel.getTotalAnswersNumber()
                val rightAnswers = viewModel.getRightAnswersNumber()
                val bundle = bundleOf(
                    resultTestInfo to TestResultData(
                        totalAnswers == rightAnswers,
                        totalAnswers,
                        rightAnswers
                    )
                )
                findNavController().navigate(R.id.action_wordTestFragment_to_resultFragment, bundle)
            }

            imgNext.setOnClickListener {
                viewModel.initLists()
            }

            imgCheck.setOnClickListener {
                when (true) {
                    binding.radioButton1.isChecked -> {
                        selectedPosition = 0
                        viewModel.listenRadioButton(selectedPosition, rightPosition)
                    }

                    binding.radioButton2.isChecked -> {
                        selectedPosition = 1
                        viewModel.listenRadioButton(selectedPosition, rightPosition)
                    }

                    binding.radioButton3.isChecked -> {
                        selectedPosition = 2
                        viewModel.listenRadioButton(selectedPosition, rightPosition)
                    }

                    binding.radioButton4.isChecked -> {
                        selectedPosition = 3
                        viewModel.listenRadioButton(selectedPosition, rightPosition)
                    }
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        viewModel.eventCreateCard.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                binding.radioGroup.visibility = View.VISIBLE
                binding.radioButton1.text = it.word1
                binding.radioButton2.text = it.word2
                binding.radioButton3.text = it.word3
                binding.radioButton4.text = it.word4
                binding.txtSelectWord.text = "Выберите подходящий вариант \n" +
                        "перевода слова ${it.textOrig}"
                println("Right answer ${it.rightPosition}")
                rightPosition = it.rightPosition
            }
        })

        viewModel.eventRadioButton1SetColor.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {

            }
        })

        viewModel.eventRadioButton1SetColor.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {

            }
        })

        viewModel.eventRadioButton1SetColor.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {

            }
        })

        viewModel.eventRadioButton1SetColor.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {

            }
        })





    }


}
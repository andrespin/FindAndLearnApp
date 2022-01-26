package android.findandlearnapp.words_test

import android.findandlearnapp.App
import android.findandlearnapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.databinding.FragmentTestBinding
import android.findandlearnapp.words_manager.WordsManagerViewModel
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.io.*


// https://www.blueappsoftware.com/how-to-read-text-file-in-android-tutorial/

// https://www.geeksforgeeks.org/how-to-read-a-text-file-in-android/

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding

    private lateinit var viewModel: TestViewModel

    private var rightPosition: Int? = null

    private var selectedPosition: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentTestBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TestViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }

        viewModel.getAllWordsFromDb()

        viewModel.initLists()

        binding.btnNext.setOnClickListener {
            binding.radioButton1.setTextColor(resources.getColor(R.color.black))
            binding.radioButton2.setTextColor(resources.getColor(R.color.black))
            binding.radioButton3.setTextColor(resources.getColor(R.color.black))
            binding.radioButton4.setTextColor(resources.getColor(R.color.black))
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = false
            viewModel.initLists()
        }

        binding.btnCheck.setOnClickListener {
            when (true) {
                binding.radioButton1.isChecked -> {
                    selectedPosition = 0
                }

                binding.radioButton2.isChecked -> {
                    selectedPosition = 1
                }

                binding.radioButton3.isChecked -> {
                    selectedPosition = 2
                }

                binding.radioButton4.isChecked -> {
                    selectedPosition = 3
                }
            }

            println("selectedPosition: $selectedPosition")

            if (selectedPosition == rightPosition) {
                when (selectedPosition) {
                    0 -> binding.radioButton1.setTextColor(resources.getColor(R.color.green))
                    1 -> binding.radioButton2.setTextColor(resources.getColor(R.color.green))
                    2 -> binding.radioButton3.setTextColor(resources.getColor(R.color.green))
                    3 -> binding.radioButton4.setTextColor(resources.getColor(R.color.green))
                }
            } else if (selectedPosition == null) {
                Toast.makeText(requireContext(), "Поле с ответом не выбрано", Toast.LENGTH_SHORT).show()
            } else {
                when (selectedPosition) {
                    0 -> binding.radioButton1.setTextColor(resources.getColor(R.color.red))
                    1 -> binding.radioButton2.setTextColor(resources.getColor(R.color.red))
                    2 -> binding.radioButton3.setTextColor(resources.getColor(R.color.red))
                    3 -> binding.radioButton4.setTextColor(resources.getColor(R.color.red))
                }
                when (rightPosition) {
                    0 -> binding.radioButton1.setTextColor(resources.getColor(R.color.green))
                    1 -> binding.radioButton2.setTextColor(resources.getColor(R.color.green))
                    2 -> binding.radioButton3.setTextColor(resources.getColor(R.color.green))
                    3 -> binding.radioButton4.setTextColor(resources.getColor(R.color.green))
                }
            }

        }

        viewModel.liveDataSendNoWordsMessage.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.liveDataCreateCardEvent.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                binding.radioGroup.visibility = View.VISIBLE
                binding.radioButton1.text = it.word1
                binding.radioButton2.text = it.word2
                binding.radioButton3.text = it.word3
                binding.radioButton4.text = it.word4
                binding.txtWordDesc.text = it.textOrig
                println("Right answer ${it.rightPosition}")
                rightPosition = it.rightPosition
            }
        })
    }
}
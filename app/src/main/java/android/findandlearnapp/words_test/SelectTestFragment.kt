package android.findandlearnapp.words_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentSelectTestBinding
import android.findandlearnapp.databinding.FragmentTestBinding
import android.findandlearnapp.dialogs.adapter.WordList
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SelectTestFragment : Fragment() {

    private lateinit var binding: FragmentSelectTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSelectTestBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            txtAll.setOnClickListener {
                findNavController().navigate(R.id.action_selectTest_to_wordTestFragment)
            }

            imgTestInfo.setOnClickListener {
                openTestInfoDialog()
            }

            txtSetTest.setOnClickListener {
                openSetTestInfoDialog()
            }

            imgSetTestInfo.setOnClickListener {
                Toast.makeText(requireActivity(), "В разработке", LENGTH_LONG).show();
            }
        }

    }

    private fun openTestInfoDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Все слова и части речи")
            .setMessage(
                "Тест генерируется на основе всех слов и всех частей речи, при желании " +
                        "вы можете настроить тест по"
            )
            .setPositiveButton(resources.getString(R.string.ru_ok)) { dialog, which ->
            }
            .show()
    }

    private fun openSetTestInfoDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Создать свой тест")
            .setMessage(
                "Вы можете выбрать списки слов, на основе которых" +
                        "будет генерироваться тест с вашими словами, также есть " +
                        "возможность выбрать" +
                        "части речи, которые будут в тесте"
            )
            .setPositiveButton(resources.getString(R.string.ru_ok)) { dialog, which ->
            }
            .show()
    }

}
package android.findandlearnapp.words_app_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentEngWordsBinding
import android.findandlearnapp.databinding.FragmentSettingsBinding
import android.findandlearnapp.words_manager.WordsManagerViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSettingsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.mainToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_settings_to_bottom_nav)
        }

        binding.btnSetLanguage.setOnClickListener {
            openLanguageDialog()
        }
    }

    private fun openLanguageDialog() {

        val singleItems = arrayOf("English", "Русский")
        var checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.ru_select_your_language_title))
            .setNeutralButton(resources.getString(R.string.ru_cancel)) { dialog, which ->
                // Respond to neutral button press
                println("setNeutralButton dialog $dialog")
                println("setNeutralButton dialog $which")
            }
            .setPositiveButton(resources.getString(R.string.ru_ok)) { dialog, which ->
                setLanguage(checkedItem)
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                checkedItem = which
            }
            .show()

    }

    private fun setLanguage(checkedItem: Int) {
        when (checkedItem) {
            0 -> println("English")
            1 -> println("Russian")
        }

    }


}
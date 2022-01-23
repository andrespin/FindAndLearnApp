package android.findandlearnapp.words_app_settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentEngWordsBinding
import android.findandlearnapp.databinding.FragmentSettingsBinding
import android.findandlearnapp.utils.getCurrentLocale
import android.findandlearnapp.utils.setLocale
import android.findandlearnapp.words_manager.WordsManagerViewModel
import android.os.LocaleList
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

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
        val currentLocale = getCurrentLocale(requireActivity())

        var checkedItem = 0

        when (currentLocale) {
            "en" -> {
                checkedItem = 0
            }
            "ru" -> {
                checkedItem = 1
            }
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.ru_select_your_language_title))
            .setNeutralButton(resources.getString(R.string.ru_cancel)) { dialog, which ->
                println("setNeutralButton dialog $dialog")
                println("setNeutralButton dialog $which")
            }
            .setPositiveButton(resources.getString(R.string.ru_ok)) { dialog, which ->
                setLanguage(checkedItem)
            }
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                checkedItem = which
            }
            .show()

    }

    private fun setLanguage(checkedItem: Int) {
        when (checkedItem) {
            0 -> setLocale("en", requireActivity())
            1 -> setLocale("ru", requireActivity())
        }

    }


//    private fun setLocale(localeToSet: String) {
//        val localeListToSet = LocaleList(Locale(localeToSet))
//        LocaleList.setDefault(localeListToSet)
//
//        resources.configuration.setLocales(localeListToSet)
//        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
//
//        val sharedPref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//        sharedPref.putString("locale_to_set", localeToSet)
//        sharedPref.apply()
//    }

}
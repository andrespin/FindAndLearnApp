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


}
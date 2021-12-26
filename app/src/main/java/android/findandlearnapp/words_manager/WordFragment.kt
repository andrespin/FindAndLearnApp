package android.findandlearnapp.words_manager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentWordBinding
import android.findandlearnapp.databinding.FragmentWordsManagerBinding
import androidx.navigation.fragment.findNavController


class WordFragment : Fragment() {

    private lateinit var binding: FragmentWordBinding

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
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
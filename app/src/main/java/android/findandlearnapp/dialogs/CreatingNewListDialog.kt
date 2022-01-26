package android.findandlearnapp.dialogs

import android.findandlearnapp.R
import android.findandlearnapp.databinding.DialogAddingBinding
import android.findandlearnapp.databinding.DialogCreatingNewListBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class CreatingNewListDialog : DialogFragment() {


    private lateinit var binding: DialogCreatingNewListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DialogCreatingNewListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {

        with(binding) {
            btnCancel.setOnClickListener {
                findNavController().popBackStack()
                Log.d("Cancel", "Pressed")
            }
            btnCreateList.setOnClickListener {
                findNavController().navigate(R.id.action_CreatingNewListDialogFragment_to_en_words)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


}
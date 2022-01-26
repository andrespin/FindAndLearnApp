package android.findandlearnapp.dialogs

import android.findandlearnapp.R
import android.findandlearnapp.databinding.DialogAddingBinding
import android.findandlearnapp.databinding.DialogFragmentCreateAddBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class AddingToListDialog : DialogFragment() {

    private lateinit var binding: DialogAddingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DialogAddingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            imgAddNewList.setOnClickListener {
                findNavController().navigate(R.id.action_AddingToListDialogFragment_to_CreatingNewListDialogFragment)
            }

            btnCancel.setOnClickListener {
                findNavController().popBackStack()
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
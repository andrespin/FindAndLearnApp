package android.findandlearnapp.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected var isWordAdded = false

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(requireActivity().supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }

}
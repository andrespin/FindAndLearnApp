package android.findandlearnapp.words_app_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.databinding.FragmentAboutAppBinding
import androidx.lifecycle.ViewModelProvider

class AboutAppFragment : Fragment() {

    private lateinit var binding: FragmentAboutAppBinding

    private lateinit var viewModel: AboutAppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAboutAppBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initListeners()
        subscribeLiveData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AboutAppViewModel::class.java)
    }

    private fun initListeners() {
        with(binding) {
            checkedTxtAboutApp.setOnClickListener {
                println("Click")
                viewModel.aboutAppClicked(checkedTxtAboutApp.isChecked)
            }
            checkedTxtApi.setOnClickListener {
                println("Click")
                viewModel.apiClicked(checkedTxtApi.isChecked)
            }
        }
    }

    private fun subscribeLiveData() {
        viewModel.liveDataEventAboutAppInvisible.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                setAboutAppInvisible()
            }
        })
        viewModel.liveDataEventAboutAppVisible.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                setAboutAppVisible()
            }
        })
        viewModel.liveDataEventApiInvisible.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                setApiInvisible()
            }
        })
        viewModel.liveDataEventApiVisible.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                setApiVisible()
            }
        })
    }


    private fun setAboutAppVisible() =
        with(binding) {
            scrollAboutApp.visibility = View.VISIBLE
            checkedTxtAboutApp.isChecked = true
            scrollApi.visibility = View.GONE
            checkedTxtApi.isChecked = false
            space.visibility = View.GONE
        }

    private fun setAboutAppInvisible() =
        with(binding) {
            scrollAboutApp.visibility = View.GONE
            checkedTxtAboutApp.isChecked = false
            space.visibility = View.VISIBLE
        }

    private fun setApiVisible() =
        with(binding) {
            scrollApi.visibility = View.VISIBLE
            checkedTxtApi.isChecked = true
            scrollAboutApp.visibility = View.GONE
            checkedTxtAboutApp.isChecked = false
            space.visibility = View.GONE
        }

    private fun setApiInvisible() =
        with(binding) {
            scrollApi.visibility = View.GONE
            checkedTxtApi.isChecked = false
            space.visibility = View.VISIBLE
        }
    
}
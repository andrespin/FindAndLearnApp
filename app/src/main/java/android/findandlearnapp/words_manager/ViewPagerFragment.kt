package android.findandlearnapp.words_manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.R
import android.findandlearnapp.databinding.FragmentViewPagerBinding
import android.findandlearnapp.databinding.FragmentWordBinding
import android.findandlearnapp.words_manager.adapter.NavHostFragmentAdapter
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentViewPagerBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(view)
    }


    private fun setupViewPager(view: View) {
        val labels = listOf("Russian", "English")
        val navGraphIds = listOf(R.navigation.ru_words_graph, R.navigation.en_words_graph)

        val navHostFragmentAdapter = NavHostFragmentAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = viewLifecycleOwner.lifecycle,
            navGraphIds = navGraphIds
        )

        viewPager = binding.viewPager
        viewPager.adapter = navHostFragmentAdapter
        val tabLayout = binding.viewPagerTabLayout

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (labels[position])
        }

        tabLayoutMediator.attach()

      //  addToolbarListener(binding.viewPagerToolbar)

    }

    private fun addToolbarListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    findNavController().navigate(R.id.settings)
                    true
                }
                else -> false
            }
        }
    }

    companion object {

        fun newInstance() =
            ViewPagerFragment()
    }
}
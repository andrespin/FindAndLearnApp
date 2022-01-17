package android.findandlearnapp.words_manager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter

// Also see https://stackoverflow.com/a/64033693
class NavHostFragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val navGraphIds: List<Int>
) : // Use this constructor to avoid this issue: https://stackoverflow.com/a/62184494
    FragmentStateAdapter(fragmentManager, lifecycle) {

    init {
        // Needs: "androidx.viewpager2:viewpager2:1.1.0-alpha01" or higher
        // Taken from: https://stackoverflow.com/a/62629996
        // Add a FragmentTransactionCallback to handle changing
        // the primary navigation fragment
        registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
            override fun onFragmentMaxLifecyclePreUpdated(
                fragment: Fragment,
                maxLifecycleState: Lifecycle.State
            ) = if (maxLifecycleState == Lifecycle.State.RESUMED) {
                // This fragment is becoming the active Fragment - set it to
                // the primary navigation fragment in the OnPostEventListener
                OnPostEventListener {
                    fragment.parentFragmentManager.commitNow {
                        setPrimaryNavigationFragment(fragment)
                    }
                }
            } else {
                super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState)
            }
        })
    }

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return NavHostFragment.create(navGraphIds[position])
    }

    override fun getItemCount(): Int = navGraphIds.size

}
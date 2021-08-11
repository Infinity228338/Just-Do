package com.domore.justdo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.domore.justdo.R
import com.domore.justdo.databinding.ActivityMainBinding
import com.domore.justdo.ui.base.BaseActivity
import com.domore.justdo.ui.categories.CategoriesFragment
import com.domore.justdo.ui.task.addTask.AddTaskFragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val navigator: Navigator =
        object : AppNavigator(activity = this, R.id.screen_container) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                setupAnimations(currentFragment, nextFragment, fragmentTransaction)
            }

        }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        savedInstanceState ?: router.newRootScreen(JustDoScreensImpl.taskListScreen())
        setBottomNavigation()
        setContentView(view)
    }


    private fun setBottomNavigation() {
        binding.bottomNavigationView.apply {
            itemIconTintList = null
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.app_bar_list -> {
                        router.navigateTo(JustDoScreensImpl.taskListScreen())
                        true
                    }
                    R.id.app_bar_add -> {
                        router.navigateTo(JustDoScreensImpl.addTaskScreen(-1L))
                        true
                    }
                    R.id.app_bar_stats -> {
                        router.navigateTo(JustDoScreensImpl.statisticsScreen())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun setupAnimations(
        currentFragment: Fragment?,
        nextFragment: Fragment,
        fragmentTransaction: FragmentTransaction
    ) {
        if (currentFragment is AddTaskFragment
            && nextFragment is CategoriesFragment
        ) {
            fragmentTransaction.setCustomAnimations(
                R.animator.slide_up,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.slide_down
            )
        }
    }
}
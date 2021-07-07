package com.domore.justdo.ui

import android.os.Bundle
import com.domore.justdo.R
import com.domore.justdo.databinding.ActivityMainBinding
import com.domore.justdo.ui.base.BaseActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(activity = this, R.id.screen_container)

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
                        router.navigateTo(JustDoScreensImpl.addTaskScreen())
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
}
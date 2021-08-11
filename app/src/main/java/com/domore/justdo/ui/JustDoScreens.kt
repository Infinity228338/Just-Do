package com.domore.justdo.ui

import com.domore.justdo.ui.task.addTask.AddTaskFragment
import com.domore.justdo.ui.categories.CategoriesFragment
import com.domore.justdo.ui.task.currenttask.CurrentTaskFragment
import com.domore.justdo.ui.stats.StatisticsFragment
import com.domore.justdo.ui.task.tasklist.TasksFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface JustDoScreens {
    fun taskListScreen(): Screen
    fun addTaskScreen(id: Long): Screen
    fun currentTaskScreen(): Screen
    fun statisticsScreen(): Screen
    fun categoriesScreen(): Screen
}

object JustDoScreensImpl : JustDoScreens {
    override fun taskListScreen() = FragmentScreen { TasksFragment.newInstance() }
    override fun addTaskScreen(id: Long): Screen =
        FragmentScreen { AddTaskFragment.newInstance(id) }

    override fun currentTaskScreen(): Screen = FragmentScreen { CurrentTaskFragment.newInstance() }
    override fun statisticsScreen(): Screen = FragmentScreen { StatisticsFragment.newInstance() }
    override fun categoriesScreen(): Screen = FragmentScreen { CategoriesFragment.newInstance() }
}

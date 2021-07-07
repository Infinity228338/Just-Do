package com.domore.justdo.ui

import com.domore.justdo.ui.addTask.AddTaskFragment
import com.domore.justdo.ui.currenttask.CurrentTaskFragment
import com.domore.justdo.ui.stats.StatisticsFragment
import com.domore.justdo.ui.tasklist.TaskListFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface JustDoScreens {
    fun taskListScreen(): Screen
    fun addTaskScreen(): Screen
    fun currentTaskScreen(): Screen
    fun statisticsScreen(): Screen
}

object JustDoScreensImpl : JustDoScreens {
    override fun taskListScreen() = FragmentScreen { TaskListFragment.newInstance() }
    override fun addTaskScreen(): Screen = FragmentScreen { AddTaskFragment.newInstance() }
    override fun currentTaskScreen(): Screen = FragmentScreen { CurrentTaskFragment.newInstance() }
    override fun statisticsScreen(): Screen = FragmentScreen { StatisticsFragment.newInstance() }
}

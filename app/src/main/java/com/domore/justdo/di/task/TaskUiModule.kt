package com.domore.justdo.di.task

import com.domore.justdo.ui.addTask.AddTaskFragment
import com.domore.justdo.ui.currenttask.CurrentTaskFragment
import com.domore.justdo.ui.stats.StatisticsFragment
import com.domore.justdo.ui.tasklist.TaskListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TaskUiModule {

    @ContributesAndroidInjector
    abstract fun bindTaskLIstFragment(): TaskListFragment

    @ContributesAndroidInjector
    abstract fun bindAddTaskFragment(): AddTaskFragment

    @ContributesAndroidInjector
    abstract fun bindStatisticsFragment(): StatisticsFragment

    @ContributesAndroidInjector
    abstract fun bindCurrentTaskFragment(): CurrentTaskFragment
}
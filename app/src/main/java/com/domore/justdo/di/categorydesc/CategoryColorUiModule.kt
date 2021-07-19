package com.domore.justdo.di.categorydesc

import com.domore.justdo.ui.categories.add.AddCategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoryColorUiModule {
    @ContributesAndroidInjector
    abstract fun bindAddFragment(): AddCategoryFragment
}
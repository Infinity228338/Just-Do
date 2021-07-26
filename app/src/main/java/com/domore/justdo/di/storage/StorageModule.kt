package com.domore.justdo.di.storage

import android.content.Context
import androidx.room.Room
import com.domore.justdo.storage.JustDoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {


    @Singleton
    @Provides
    fun provideDatabaseGitHubStorage(context: Context): JustDoDatabase =
        Room.databaseBuilder(context, JustDoDatabase::class.java, "justdo.db")
            .fallbackToDestructiveMigration()
            .build()

}
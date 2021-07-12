package com.domore.justdo.di.storage

import android.content.Context
import androidx.room.Room
import com.domore.justdo.data.storage.JustDoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

//    @InMemory
//    @Singleton
//    @Provides
//    fun provideInMemoryGitHubStorage(context: Context): JustDoDatabase =
//        Room.inMemoryDatabaseBuilder(context, JustDoDatabase::class.java)
//            .fallbackToDestructiveMigration()
//            .build()

    @Singleton
    @Provides
    fun provideDatabaseGitHubStorage(context: Context): JustDoDatabase =
        Room.databaseBuilder(context, JustDoDatabase::class.java, "justdo.db")
            .build()

}
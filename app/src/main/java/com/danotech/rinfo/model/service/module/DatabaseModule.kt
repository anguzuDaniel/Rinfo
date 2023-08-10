package com.danotech.rinfo.model.service.module

import android.content.Context
import com.danotech.rinfo.data.LocalOfflineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalOfflineDatabase(@ApplicationContext context: Context): LocalOfflineDatabase {
        return LocalOfflineDatabase.getDatabase(context)
    }
}

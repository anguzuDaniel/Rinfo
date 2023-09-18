package com.danotech.rinfo.model.service.module

import android.content.Context
import android.util.Patterns
import com.danotech.rinfo.model.service.impl.DataStoreUtil
import com.danotech.rinfo.model.service.impl.EmailValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context): DataStoreUtil =
        DataStoreUtil(context)

    @Provides
    @Singleton
    fun provideEmailValidator(): EmailValidator {
        return EmailValidator()
    }
}
package com.danotech.rinfo.model.service.module

import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.ConfigurationService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.model.service.StorageService
import com.danotech.rinfo.model.service.impl.AccountServiceImpl
import com.danotech.rinfo.model.service.impl.BusinessAccountServiceImpl
import com.danotech.rinfo.model.service.impl.ConfigurationServiceImpl
import com.danotech.rinfo.model.service.impl.LogServiceImpl
import com.danotech.rinfo.model.service.impl.ProfileServiceImpl
import com.danotech.rinfo.model.service.impl.ReviewServiceImpl
import com.danotech.rinfo.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService

    @Binds
    abstract fun provideProfileService(impl: ProfileServiceImpl): ProfileService

    @Binds
    abstract fun provideBusinessAccountService(impl: BusinessAccountServiceImpl): BusinessAccountService

    @Binds
    abstract fun provideReviewService(impl: ReviewServiceImpl): ReviewService
}

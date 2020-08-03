package com.almarai.easypick.di.hilt

import com.almarai.repository.api.*
import com.almarai.repository.implementations.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideAppUpdateRepository(
        appUpdateRepositoryImplementation: AppUpdateRepositoryImplementation
    ): AppUpdateRepository

    @Binds
    abstract fun provideApplicationRepository(applicationRepositoryImplementation: ApplicationRepositoryImplementation): ApplicationRepository

    @Binds
    abstract fun provideRoutesRepository(
        routesRepositoryImplementation: RoutesRepositoryImplementation
    ): RoutesRepository

    @Binds
    abstract fun provideProductsRepository(productsRepositoryImplementation: ProductsRepositoryImplementation): ProductsRepository

    @Binds
    abstract fun provideStatisticsRepository(statisticsRepositoryImplementation: StatisticsRepositoryImplementation): StatisticsRepository
}
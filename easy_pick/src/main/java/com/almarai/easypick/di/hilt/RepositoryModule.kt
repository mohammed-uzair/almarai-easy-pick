package com.almarai.easypick.di.hilt

import com.almarai.repository.api.*
import com.almarai.repository.implementations.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
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

    @Binds
    abstract fun provideFileUploadRepository(ticketRepositoryImplementation: TicketRepositoryImplementation): TicketRepository
}
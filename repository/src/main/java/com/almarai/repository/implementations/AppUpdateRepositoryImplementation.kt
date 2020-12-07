package com.almarai.repository.implementations

import com.almarai.data.easy_pick_models.AppUpdate
import com.almarai.easypick.data_source.interfaces.AppUpdateDataSource
import com.almarai.repository.api.AppUpdateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppUpdateRepositoryImplementation
@Inject constructor(
    private val appUpdateDataSource: AppUpdateDataSource
) :
    AppUpdateRepository {
    companion object {
        const val TAG = "AppUpdateRepoImpl"
    }

    override suspend fun checkAppUpdate() =
        appUpdateDataSource.checkAppUpdate()
}
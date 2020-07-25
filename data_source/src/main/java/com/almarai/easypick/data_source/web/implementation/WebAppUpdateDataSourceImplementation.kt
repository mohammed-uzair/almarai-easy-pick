package com.almarai.easypick.data_source.web.implementation

import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.interfaces.WebAppUpdateDataSource

class WebAppUpdateDataSourceImplementation(private val webService: WebService) :
    WebAppUpdateDataSource {
    override suspend fun checkAppUpdate() = webService.appUpdateApi.checkAppUpdate()
}
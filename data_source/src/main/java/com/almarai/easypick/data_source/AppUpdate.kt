package com.almarai.easypick.data_source

import com.almarai.data.easy_pick_models.AppUpdate
import kotlinx.coroutines.channels.Channel

var APP_UPDATE: Channel<AppUpdate>? = Channel()
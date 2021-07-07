package com.almarai.easypick.di.hilt

import android.content.Context
import com.almarai.data.easy_pick_models.adapters.JsonParsingGroupAdapter
import com.almarai.data.easy_pick_models.adapters.JsonParsingProductStatusAdapter
import com.almarai.data.easy_pick_models.adapters.JsonParsingRouteStatusAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideMoshi(): Moshi {
        val moshi = Moshi.Builder()
            .add(JsonParsingRouteStatusAdapter())
            .add(JsonParsingGroupAdapter())
            .add(JsonParsingProductStatusAdapter())
            .add(KotlinJsonAdapterFactory())

        return moshi.build()
    }

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) = context
}
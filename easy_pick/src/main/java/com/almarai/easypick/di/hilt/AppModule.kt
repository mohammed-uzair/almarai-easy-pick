package com.almarai.easypick.di.hilt

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {
    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) = context
}
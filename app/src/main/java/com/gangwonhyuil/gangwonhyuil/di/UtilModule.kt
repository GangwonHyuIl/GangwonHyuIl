package com.gangwonhyuil.gangwonhyuil.di

import android.content.Context
import com.gangwonhyuil.gangwonhyuil.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Singleton
    @Provides
    fun provideFileUtil(
        @ApplicationContext context: Context,
    ): FileUtil = FileUtil(context)
}
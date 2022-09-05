package com.tcl.characterapp.di

import android.content.Context
import android.content.SharedPreferences
import comprmto.rickyandmorty.util.Constant.SET_SHARED_PREF_KEY
import comprmto.rickyandmorty.util.Constant.SHARED_PREF_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SET_SHARED_PREF_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserId(sharedPreferences: SharedPreferences): String =
        sharedPreferences.getString(SHARED_PREF_KEY, null).toString()
}
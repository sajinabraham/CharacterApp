package com.tcl.characterapp.di

import android.content.Context
import androidx.room.Room
import com.tcl.characterapp.data.local.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    fun provideCharacterDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CharacterDatabase::class.java, "FavoriteDatabase"
    ).build()

    @Provides
    fun provideCharacterDao(database: CharacterDatabase) = database.characterDao
}
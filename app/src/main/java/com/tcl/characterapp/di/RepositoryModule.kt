package com.tcl.characterapp.di

import com.tcl.characterapp.data.repository.FirebaseAuthRepositoryImpl
import com.tcl.characterapp.data.repository.RepositoryImpl
import com.tcl.characterapp.domain.repository.FirebaseAuthRepository
import com.tcl.characterapp.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: RepositoryImpl): Repository

    @Binds
    abstract fun bindFirebaseAuthRepository(firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository
}
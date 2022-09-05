package com.tcl.characterapp.domain.usecase.firebase

import com.tcl.characterapp.di.IoDispatcher
import com.tcl.characterapp.domain.repository.FirebaseAuthRepository
import com.tcl.characterapp.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val currentUser = firebaseAuthRepository.getCurrentUserId()
            emit(Resource.Success(currentUser))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}
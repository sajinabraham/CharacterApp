package com.tcl.characterapp.domain.usecase.firebase


import com.tcl.characterapp.di.IoDispatcher
import com.tcl.characterapp.domain.model.AuthModel
import com.tcl.characterapp.domain.repository.FirebaseAuthRepository
import com.tcl.characterapp.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CreateUserUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(authModel: AuthModel) = flow {
        emit(Resource.Loading)
        try {
            val auth = firebaseAuthRepository.signUpWithEmail(authModel)
            emit(Resource.Success(auth))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}
package com.tcl.characterapp.domain.usecase.firebase


import com.tcl.characterapp.di.IoDispatcher
import com.tcl.characterapp.domain.model.AuthModel
import com.tcl.characterapp.domain.repository.FirebaseAuthRepository
import com.tcl.characterapp.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(authModel: AuthModel) = flow {
        emit(Resource.Loading)
        try {
            repository.signIn(authModel)?.let {
                emit(Resource.Success(null))
            }
        } catch (e: Exception) {
        }
    }.flowOn(ioDispatcher)
}
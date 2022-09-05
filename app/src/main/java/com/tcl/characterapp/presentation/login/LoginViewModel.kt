package com.tcl.characterapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.tcl.characterapp.data.repository.Repo
import com.tcl.characterapp.domain.model.AuthModel
import com.tcl.characterapp.domain.model.User
import com.tcl.characterapp.domain.usecase.firebase.GetCurrentUserUseCase
import com.tcl.characterapp.domain.usecase.firebase.LoginUseCase
import com.tcl.characterapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val repo: Repo,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()
    private val gMailUserLiveData = MutableLiveData<Resource<User>>()

    fun handleEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.Login -> {
                login(uiEvent.authModel)
            }
            is LoginUiEvent.GetCurrentUser -> {
                getCurrentUser()
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(currentUser = resultState.data)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun login(authModel: AuthModel) {
        viewModelScope.launch {
            loginUseCase.invoke(authModel).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isLoggedIn = true)
                        }
                    }

                    is Resource.Error -> {

                    }
                }
            }
        }
    }

     fun signInWithGoogle(acct: GoogleSignInAccount): LiveData<Resource<User>> {
        repo.signInWithGoogle(acct).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                gMailUserLiveData.postValue(
                    Resource.Success(
                        User(firebaseAuth.currentUser?.email!!, firebaseAuth.currentUser?.displayName!!)
                    )
                )
            } else {
                gMailUserLiveData.postValue(Resource.Error("couldn't sign in user"))
            }

        }
        return gMailUserLiveData
    }
}
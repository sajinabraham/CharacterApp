package com.tcl.characterapp.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
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
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()

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
                    else -> {}
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
                    else -> {}
                }
            }
        }
    }
}
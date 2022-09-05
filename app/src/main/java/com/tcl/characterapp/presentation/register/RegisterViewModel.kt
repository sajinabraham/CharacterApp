package com.tcl.characterapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcl.characterapp.domain.model.AuthModel
import com.tcl.characterapp.domain.usecase.firebase.CreateUserUseCase
import com.tcl.characterapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
) :
    ViewModel() {

    private val uiState = MutableStateFlow(RegisterUiState())
    val _uiState: StateFlow<RegisterUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: RegisterUiEvent) {
        when (uiEvent) {
            is RegisterUiEvent.CreateUser -> {
                createUser(uiEvent.authModel)
            }
        }
    }

    private fun createUser(authModel: AuthModel) {
        viewModelScope.launch {
            createUserUseCase.invoke(authModel).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isRegister = true)
                        }
                    }
                    is Resource.Error -> {
                        uiState.update {
                            it.copy(error = null)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}
package com.tcl.characterapp.presentation.register



import com.tcl.characterapp.domain.model.AuthModel

sealed class RegisterUiEvent {
    data class CreateUser(val authModel: AuthModel) : RegisterUiEvent()
}
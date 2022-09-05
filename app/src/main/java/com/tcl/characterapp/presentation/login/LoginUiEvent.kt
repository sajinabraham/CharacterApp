package com.tcl.characterapp.presentation.login

import com.tcl.characterapp.domain.model.AuthModel

sealed class LoginUiEvent {

    data class Login(var authModel: AuthModel) : LoginUiEvent()
    object GetCurrentUser : LoginUiEvent()
}

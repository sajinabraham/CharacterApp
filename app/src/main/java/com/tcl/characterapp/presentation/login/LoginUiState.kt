package com.tcl.characterapp.presentation.login


data class LoginUiState(
    val isLoggedIn: Boolean? = false,
    val currentUser: String? = null
)

package com.tcl.characterapp.presentation.register

import com.google.firebase.auth.FirebaseUser

data class RegisterUiState(
    var error: String? = null,
    val isRegister: Boolean? = false,
    val currentUser: String? = null,
    val firebaseUser: FirebaseUser? = null
)

package com.tcl.characterapp.domain.repository


import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tcl.characterapp.domain.model.AuthModel


interface FirebaseAuthRepository {
    suspend fun signUpWithEmail(authModel: AuthModel): FirebaseUser?
    suspend fun getCurrentUserId(): String?
    suspend fun signIn(authModel: AuthModel): FirebaseUser?
}
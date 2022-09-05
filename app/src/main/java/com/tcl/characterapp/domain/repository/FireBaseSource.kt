package com.tcl.characterapp.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FireBaseSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun signInWithGoogle(acct: GoogleSignInAccount) =
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken, null))

}
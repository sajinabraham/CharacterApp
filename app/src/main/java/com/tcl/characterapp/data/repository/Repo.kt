package com.tcl.characterapp.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.tcl.characterapp.domain.repository.FireBaseSource
import javax.inject.Inject

class Repo @Inject constructor(private val fireBaseSource: FireBaseSource) {

     fun signInWithGoogle(acct: GoogleSignInAccount) = fireBaseSource.signInWithGoogle(acct)

}

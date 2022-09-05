package com.tcl.characterapp.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.tcl.characterapp.R
import com.tcl.characterapp.databinding.FragmentLoginBinding
import com.tcl.characterapp.domain.model.AuthModel
import com.tcl.characterapp.utils.NetworkListener
import com.tcl.characterapp.utils.Resource
import comprmto.rickyandmorty.util.Constant
import comprmto.rickyandmorty.util.Constant.BLANK_FIELD_ERROR
import comprmto.rickyandmorty.util.Constant.CHECK_YOUR_CONNCETION
import comprmto.rickyandmorty.util.Constant.FAILED_LOGIN
import comprmto.rickyandmorty.util.Constant.RC_SIGN_IN
import comprmto.rickyandmorty.util.Constant.SUCCESS_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var connectionLiveData: NetworkListener
    private val loginViewModel: LoginViewModel by viewModels()
    private var loginBinding: FragmentLoginBinding? = null

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(inflater)
        connectionLiveData = NetworkListener(requireContext())
        return loginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectionLiveData.observe(requireActivity()) { isNetworkAvailable ->
            if (isNetworkAvailable == true) {
                initListeners()
            } else {
                Snackbar.make(
                    requireView(),
                    CHECK_YOUR_CONNCETION,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

    }

    private fun initListeners() {

        loginBinding?.apply {

            googleSignIn.setOnClickListener {
                signIn()
            }
            loginBtn.setOnClickListener {
                val userEmail = emailEt.text.toString().trim()
                val userPassword = PassEt.text.toString().trim()
                if (userEmail.isEmpty()) {
                    emailEt.error = BLANK_FIELD_ERROR
                } else if (userPassword.isEmpty()) {
                    PassEt.error = BLANK_FIELD_ERROR
                } else {
                    val auth = AuthModel(userEmail, userPassword)
                    loginViewModel.handleEvent(LoginUiEvent.Login(auth))
                    lifecycleScope.launch {
                        loginViewModel._uiState.collect { state ->

                            state.isLoggedIn.let { loggedIn ->
                                if (loggedIn == true) {
                                    Snackbar.make(
                                        requireView(),
                                        SUCCESS_LOGIN,
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    val sharedPref =
                                        activity?.getSharedPreferences(
                                            "getSharedPref",
                                            Context.MODE_PRIVATE
                                        )
                                    with(sharedPref?.edit()) {
                                        this?.putString(Constant.SHARED_PREF_KEY, userEmail)
                                        this?.apply()
                                    }
                                    findNavController().navigate(R.id.characterFragment)
                                } else {
                                    Snackbar.make(
                                        requireView(),
                                        FAILED_LOGIN,
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
            signUpTv.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_RegisterFragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                loginViewModel.signInWithGoogle(account!!).observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {

                            Log.e("email", "email" + it.data?.email)
                        }
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun signIn() {

        val signInIntent: Intent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onDestroy() {
        super.onDestroy()
        loginBinding = null
    }
}
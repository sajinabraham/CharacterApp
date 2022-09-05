package com.tcl.characterapp.presentation.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tcl.characterapp.R
import com.tcl.characterapp.databinding.FragmentRegisterBinding
import com.tcl.characterapp.domain.model.AuthModel
import comprmto.rickyandmorty.util.Constant
import comprmto.rickyandmorty.util.Constant.BLANK_FIELD_ERROR
import comprmto.rickyandmorty.util.Constant.SUCCESS_REGISTER
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var registerBinding: FragmentRegisterBinding? = null
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterBinding.inflate(inflater)
        return registerBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        registerBinding?.apply {
            signUpBtn.setOnClickListener {
                val userEmail = signUpEmail.text.toString().trim()
                val userPassword = signUpPassword.text.toString().trim()
                val name = signUpName.text.toString().trim()
                if (userEmail.isEmpty()) {
                    signUpEmail.error = BLANK_FIELD_ERROR
                } else if (userPassword.isEmpty()) {
                    signUpPassword.error = BLANK_FIELD_ERROR
                } else if (name.isEmpty()) {
                    signUpName.error = BLANK_FIELD_ERROR
                } else {
                    val auth = AuthModel(userEmail, userPassword)
                    registerViewModel.handleEvent(RegisterUiEvent.CreateUser(auth))
                    it.hideKeyboard()
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            registerViewModel._uiState.collect { state ->
                                state.isRegister.let { isRegister ->
                                    if (isRegister == true) {
                                        Snackbar.make(
                                            requireView(),
                                            SUCCESS_REGISTER,
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
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        registerBinding = null
    }
}
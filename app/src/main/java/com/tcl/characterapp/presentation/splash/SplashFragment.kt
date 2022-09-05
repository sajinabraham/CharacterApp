package com.tcl.characterapp.presentation.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tcl.characterapp.R
import com.tcl.characterapp.databinding.FragmentSplashBinding
import comprmto.rickyandmorty.util.Constant.SHARED_PREF_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var splashBinding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashBinding = FragmentSplashBinding.inflate(inflater)
        return splashBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences(
            "getSharedPref", Context.MODE_PRIVATE
        )
        val pref = sharedPref?.getString(SHARED_PREF_KEY, null)
        Handler(Looper.myLooper()!!).postDelayed({
            if (pref != null) {
                findNavController().navigate(R.id.characterFragment)
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        splashBinding = null
    }
}
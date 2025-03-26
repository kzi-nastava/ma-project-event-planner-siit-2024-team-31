package com.example.ep2024.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.ep2024.MainActivity
import com.example.ep2024.R
import com.example.ep2024.config.SecureStorage
import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO
import com.example.ep2024.databinding.FragmentLoginBinding
import com.example.ep2024.domain.model.Role
import com.example.ep2024.domain.repository.AuthRepository
import com.example.ep2024.domain.usecase.auth.LoginUseCase
import com.google.gson.Gson
import javax.inject.Inject

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val userRole: Role? = null

    @JvmField
    @Inject
    var secureStorage: SecureStorage? = null

    @JvmField
    @Inject
    var authRepository: AuthRepository? = null

    private var loginUseCase: LoginUseCase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        requireActivity()
        (activity as MainActivity).appComponent.inject(this)

        loginUseCase = LoginUseCase(authRepository)

        return binding!!.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.buttonLogin.setOnClickListener {
            val email = binding!!.editTextEmail.text.toString().trim { it <= ' ' }
            val password = binding!!.editTextPassword.text.toString().trim { it <= ' ' }

            //make it in a separate thread to avoid blocking the UI
            Thread {
                try {
                    val loginRequest = LoginRequestDTO(email, password)
                    val loginResponse = loginUseCase!!.execute(loginRequest)

                    secureStorage!!.saveUser(Gson().toJson(loginResponse))

                    if (activity != null) {
                        requireActivity().runOnUiThread {
                            val mainActivity = activity as MainActivity?
                            mainActivity?.updateBottomNavigationMenu()
                            NavHostFragment.findNavController(this@LoginFragment)
                                .navigate(R.id.mainPageFragment)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (activity != null) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                activity,
                                "Auth Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
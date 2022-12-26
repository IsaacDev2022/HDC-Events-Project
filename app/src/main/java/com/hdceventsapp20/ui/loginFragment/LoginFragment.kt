package com.hdceventsapp20.ui.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hdceventsapp20.R
import com.hdceventsapp20.databinding.FragmentLoginBinding
import com.hdceventsapp20.model.dao.userDAO.UserDAO
import com.hdceventsapp20.model.db.AppDatabase
import com.hdceventsapp20.repository.userRepository.UserDataSource
import com.hdceventsapp20.repository.userRepository.UserRepository

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // private val loginViewModel: LoginViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelCLass: Class<T>): T {
                val userDAO: UserDAO = AppDatabase.getInstance(requireContext()).userDAO
                val userRepository: UserRepository = UserDataSource(userDAO)
                return LoginViewModel(userRepository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenToAuthentication()
        setRegisterUser()
    }

    private fun listenToAuthentication() {
        loginViewModel.authenticationStateEvent.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    findNavController().popBackStack()
                }

                else -> {}
            }
        }

        loginViewModel.messageAuthenticationState.observe(viewLifecycleOwner) { string ->
            Snackbar.make(requireView(), string, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setRegisterUser() {
        binding.loginButton.setOnClickListener {
            val email = binding.inputLoginUsername.text.toString()
            val password = binding.inputLoginPassword.text.toString()

            loginViewModel.authentication(email, password)
        }

        binding.tvRegisterUser.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerUserFragment)
        }
    }
}
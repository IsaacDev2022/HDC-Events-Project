package com.hdceventsapp20.ui.profileFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hdceventsapp20.databinding.FragmentProfileBinding
import com.hdceventsapp20.model.dao.userDAO.UserDAO
import com.hdceventsapp20.model.db.AppDatabase
import com.hdceventsapp20.repository.userRepository.UserDataSource
import com.hdceventsapp20.repository.userRepository.UserRepository
import com.hdceventsapp20.ui.loginFragment.LoginViewModel

class ProfileFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelCLass: Class<T>): T {
                val userDAO: UserDAO = AppDatabase.getInstance(requireContext()).userDAO
                val userRepository: UserRepository = UserDataSource(userDAO)
                return LoginViewModel(userRepository) as T
            }
        }
    }

    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.authenticationStateEvent.observe(viewLifecycleOwner) { authentication ->
            when (authentication) {
                is LoginViewModel.AuthenticationState.Authenticated -> {

                }

                else -> {}
            }
        }
    }
}
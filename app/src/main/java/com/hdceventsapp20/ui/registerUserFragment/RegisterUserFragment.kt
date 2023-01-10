package com.hdceventsapp20.ui.registerUserFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hdceventsapp20.R
import com.hdceventsapp20.databinding.FragmentRegisterUserBinding
import com.hdceventsapp20.model.dao.userDAO.UserDAO
import com.hdceventsapp20.model.db.AppDatabase
import com.hdceventsapp20.repository.userRepository.UserDataSource
import com.hdceventsapp20.repository.userRepository.UserRepository

class RegisterUserFragment : Fragment() {

    private val viewModel: RegisterUserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelCLass: Class<T>): T {
                val userDAO: UserDAO = AppDatabase.getInstance(requireContext()).userDAO
                val userRepository: UserRepository = UserDataSource(userDAO)
                return RegisterUserViewModel(userRepository) as T
            }
        }
    }

    private var _binding: FragmentRegisterUserBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()
        registerUser()
    }

    private fun observeEvents() {
        viewModel.userStateDate.observe(viewLifecycleOwner) { user ->
            when(user) {
                is RegisterUserViewModel.UserState.InsertedUser -> {
                    findNavController().popBackStack()
                }
                else -> {}
            }
        }

        viewModel.messageUserStateData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun registerUser() {
        binding.btnRegisterUser.setOnClickListener {
            val userName = binding.inputUserName.text.toString()
            val userCpf = binding.inputCpf.text.toString()
            val userAge = binding.inputAge.text.toString()
            val userEmail = binding.inputEmail.text.toString()
            val userPassword = binding.inputPassword.text.toString()

            viewModel.insertUser(
                userName,
                userCpf,
                userAge,
                userEmail,
                userPassword,
            )
        }
    }
}
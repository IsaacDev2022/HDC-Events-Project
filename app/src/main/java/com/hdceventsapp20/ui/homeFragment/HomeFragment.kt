package com.hdceventsapp20.ui.homeFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hdceventsapp20.R
import com.hdceventsapp20.databinding.FragmentHomeBinding
import com.hdceventsapp20.model.dao.eventDAO.EventDAO
import com.hdceventsapp20.model.dao.userDAO.UserDAO
import com.hdceventsapp20.model.db.AppDatabase
import com.hdceventsapp20.repository.eventRepository.EventDataSource
import com.hdceventsapp20.repository.eventRepository.EventRepository
import com.hdceventsapp20.repository.userRepository.UserDataSource
import com.hdceventsapp20.repository.userRepository.UserRepository
import com.hdceventsapp20.ui.loginFragment.LoginFragment
import com.hdceventsapp20.ui.loginFragment.LoginViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelCLass: Class<T>): T {
                val eventDAO: EventDAO = AppDatabase.getInstance(requireContext()).eventDAO
                val eventRepository: EventRepository = EventDataSource(eventDAO)
                return HomeViewModel(eventRepository) as T
            }
        }
    }

    private val loginViewModel: LoginViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelCLass: Class<T>): T {
                val userDAO: UserDAO = AppDatabase.getInstance(requireContext()).userDAO
                val userRepository: UserRepository = UserDataSource(userDAO)
                return LoginViewModel(userRepository) as T
            }
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAuthenticated()
        observeEvents()
        setFragmentRegisterEvent()
    }

    private fun checkAuthenticated() {
        loginViewModel._authenticationStateEvent.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Unauthenticated -> {
                    findNavController().navigate(R.id.loginFragment)
                }
                else -> {}
            }
        }
    }

    private fun observeEvents() {
        viewModel.allEventsState.observe(viewLifecycleOwner) { allEvents ->
            val eventListAdapter = EventListAdapter(allEvents).apply {
                onItemUpdateClick = { event ->
                    val direction = HomeFragmentDirections
                        .actionHomeFragmentToEventFragment(event)
                    findNavController().navigate(direction)
                }

                onItemExpansiveClick = { event ->
                    val direction = HomeFragmentDirections
                        .actionHomeFragmentToExpandedEventFragment(event)
                    findNavController().navigate(direction)
                }
            }

            with(binding.recyclerView) {
                setHasFixedSize(true)
                adapter = eventListAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEvents()
    }

    private fun setFragmentRegisterEvent() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eventFragment)
        }
    }
}
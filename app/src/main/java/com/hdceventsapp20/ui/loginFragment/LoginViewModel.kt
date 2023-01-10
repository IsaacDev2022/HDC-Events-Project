package com.hdceventsapp20.ui.loginFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.hdceventsapp20.R
import com.hdceventsapp20.model.entities.eventEntity.Event
import com.hdceventsapp20.model.entities.userEntity.User
import com.hdceventsapp20.repository.userRepository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    sealed class AuthenticationState {
        object Unauthenticated : AuthenticationState()
        object Authenticated : AuthenticationState()
        class InvalidAuthentication(val fields: List<Pair<String, Int>>) : AuthenticationState()
    }

    val _authenticationStateEvent = MutableLiveData<AuthenticationState>()

    private val _messageAuthenticationState = MutableLiveData<Int>()
    val messageAuthenticationState: LiveData<Int>
        get() = _messageAuthenticationState

    private val _userState = MutableLiveData<List<User>>()
    val userState : LiveData<List<User>>
        get() = _userState

    init {
        refuseAuthentication()
    }

    fun refuseAuthentication() {
        _authenticationStateEvent.value = AuthenticationState.Unauthenticated
    }

    fun authentication(email: String, password: String) = viewModelScope.launch {
        val repository = userRepository.selectUser(email, password)
        _userState.postValue(userRepository.selectUser(email, password))

        if (repository.isNotEmpty()) {
            _authenticationStateEvent.value = AuthenticationState.Authenticated
        } else {
            _authenticationStateEvent.value = AuthenticationState.Unauthenticated
            _messageAuthenticationState.value = R.string.error_authentication
        }
    }

    private fun isValidForm(username: String, password: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()

        if (username.isEmpty()) {
            invalidFields.add(INPUT_USERNAME)
        }

        if (password.isEmpty()) {
            invalidFields.add(INPUT_PASSWORD)
        }

        if (invalidFields.isNotEmpty()) {
            _authenticationStateEvent.value = AuthenticationState.InvalidAuthentication(invalidFields)
            return false
        }

        return true
    }

    companion object {
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.login_input_layout_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.login_input_layout_error_invalid_password
    }
}
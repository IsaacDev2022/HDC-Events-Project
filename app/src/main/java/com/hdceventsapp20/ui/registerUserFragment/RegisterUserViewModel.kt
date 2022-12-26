package com.hdceventsapp20.ui.registerUserFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdceventsapp20.R
import com.hdceventsapp20.repository.userRepository.UserRepository
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userStateData = MutableLiveData<UserState>()
    val userStateDate: LiveData<UserState>
        get() = _userStateData

    private val _messageUserStateData = MutableLiveData<Int>()
    val messageUserStateData: LiveData<Int>
        get() = _messageUserStateData

    fun insertUser(
        nameUser: String,
        cpfUser: String,
        ageUser: String,
        email: String,
        password: String,
    ) = viewModelScope.launch {
        try {
            val idUser = userRepository.insertUser(
                nameUser,
                cpfUser,
                ageUser,
                email,
                password,
            )
            if (idUser > 0) {
                _userStateData.value = UserState.InsertedUser
                _messageUserStateData.value = R.string.success_user_inserted
            }

        } catch (ex: Exception) {
            _messageUserStateData.value = R.string.error_user_register
        }
    }

    sealed class UserState {
        object InsertedUser: UserState()
        object LoggedUser: UserState()
    }
}
package com.hdceventsapp20.repository.userRepository

import com.hdceventsapp20.model.entities.userEntity.User

interface UserRepository {

    suspend fun insertUser(
        nameUser: String,
        cpfUser: String,
        ageUser: String,
        email: String,
        password: String,
    ) : Long

    suspend fun selectUser(email: String, password: String): List<User>
}
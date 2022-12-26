package com.hdceventsapp20.repository.userRepository

import com.hdceventsapp20.model.dao.userDAO.UserDAO
import com.hdceventsapp20.model.entities.userEntity.User

class UserDataSource(
    private val userDAO: UserDAO
) : UserRepository {

    override suspend fun insertUser(
        nameUser: String,
        cpfUser: String,
        ageUser: String,
        email: String,
        password: String
    ): Long {
        val user = User(
            nameUser = nameUser,
            cpfUser = cpfUser,
            ageUser = ageUser,
            email = email,
            password = password
        )

        return userDAO.insertUser(user)
    }

    override suspend fun selectUser(email: String, password: String): List<User> {
        return userDAO.selectUser(email, password)
    }
}
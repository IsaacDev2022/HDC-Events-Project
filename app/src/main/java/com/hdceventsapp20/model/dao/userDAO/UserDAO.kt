package com.hdceventsapp20.model.dao.userDAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hdceventsapp20.model.entities.eventEntity.Event
import com.hdceventsapp20.model.entities.userEntity.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun selectUser(email: String, password: String): List<User>
}
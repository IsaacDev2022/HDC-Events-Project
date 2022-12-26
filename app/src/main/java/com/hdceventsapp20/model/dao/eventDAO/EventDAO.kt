package com.hdceventsapp20.model.dao.eventDAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hdceventsapp20.model.entities.eventEntity.Event

@Dao
interface EventDAO {
    @Insert
    suspend fun insert(event: Event): Long

    @Update
    suspend fun update(event: Event)

    @Query("SELECT * FROM event_tb")
    suspend fun getAll(): List<Event>

    @Query("DELETE FROM event_tb WHERE idEvent = :id")
    suspend fun delete(id: Long)
}
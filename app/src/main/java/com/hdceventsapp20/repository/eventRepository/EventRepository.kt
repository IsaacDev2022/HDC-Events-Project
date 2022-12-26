package com.hdceventsapp20.repository.eventRepository

import com.hdceventsapp20.model.entities.eventEntity.Event

interface EventRepository {

    suspend fun insert(
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String
    ): Long

    suspend fun update(
        idEvent: Long,
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String
    )

    suspend fun getAll(): List<Event>

    suspend fun delete(id: Long)
}
package com.hdceventsapp20.repository.eventRepository

import com.hdceventsapp20.model.dao.eventDAO.EventDAO
import com.hdceventsapp20.model.entities.eventEntity.Event

class EventDataSource(
    private val eventDAO: EventDAO
) : EventRepository {

    override suspend fun insert(
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String
    ): Long {
        val event = Event(
            nameEvent = nameEvent,
            dateEvent = dateEvent,
            timeEvent = timeEvent,
            city = city,
            type = type,
            privateOrPublic = privateOrPublic,
            description = description
        )

        return eventDAO.insert(event)
    }

    override suspend fun update(
        idEvent: Long,
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String
    ) {
        val event = Event(
            idEvent = idEvent,
            nameEvent = nameEvent,
            dateEvent = dateEvent,
            timeEvent = timeEvent,
            city = city,
            type = type,
            privateOrPublic = privateOrPublic,
            description = description
        )

        return eventDAO.update(event)
    }

    override suspend fun getAll(): List<Event> {
        return eventDAO.getAll()
    }

    override suspend fun delete(id: Long) {
        eventDAO.delete(id)
    }
}
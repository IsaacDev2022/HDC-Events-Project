package com.hdceventsapp20.ui.eventFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdceventsapp20.R
import com.hdceventsapp20.repository.eventRepository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _eventStateData = MutableLiveData<EventState>()
    val eventStateData: LiveData<EventState>
        get() = _eventStateData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun addOrUpdate(
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String,
        idEvent: Long
    ) = viewModelScope.launch {
        if (idEvent > 0) {
            updateEvent(
                idEvent,
                nameEvent,
                dateEvent,
                timeEvent,
                city,
                type,
                privateOrPublic,
                description
            )
        } else {
            insertEvent(
                nameEvent,
                dateEvent,
                timeEvent,
                city,
                type,
                privateOrPublic,
                description
            )
        }
    }

    fun insertEvent(
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String
    ) = viewModelScope.launch {
        try {
            val id = eventRepository.insert(
                nameEvent,
                dateEvent,
                timeEvent,
                city,
                type,
                privateOrPublic,
                description
            )
            if (id > 0) {
                _eventStateData.value = EventState.Inserted
                _messageEventData.value = R.string.success_inserted
            }

        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    fun updateEvent(
        idEvent: Long,
        nameEvent: String,
        dateEvent: String,
        timeEvent: String,
        city: String,
        type: String,
        privateOrPublic: String,
        description: String) = viewModelScope.launch {
        try {
            eventRepository.update(
                idEvent,
                nameEvent,
                dateEvent,
                timeEvent,
                city,
                type,
                privateOrPublic,
                description
            )
            _eventStateData.value = EventState.Updated
            _messageEventData.value = R.string.success_updated

        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    fun removeEvent(idEvent: Long) = viewModelScope.launch {
        try {
            if (idEvent > 0) {
                eventRepository.delete(idEvent)
                _eventStateData.value = EventState.Deleted
                _messageEventData.value = R.string.success_deleted
            }

        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    sealed class EventState {
        object Inserted: EventState()
        object Updated: EventState()
        object Deleted: EventState()
    }

    companion object {
        private val TAG = EventViewModel::class.java.simpleName
    }
}
package com.hdceventsapp20.ui.expandedEventFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdceventsapp20.model.entities.eventEntity.Event
import com.hdceventsapp20.repository.eventRepository.EventRepository
import kotlinx.coroutines.launch

class ExpandedEventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _allEventsState = MutableLiveData<List<Event>>()
    val allEventsState: LiveData<List<Event>>
        get() = _allEventsState

    fun getEvents() = viewModelScope.launch {
        _allEventsState.postValue(eventRepository.getAll())
    }
}
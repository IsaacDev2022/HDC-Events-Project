package com.hdceventsapp20.ui.homeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdceventsapp20.R
import com.hdceventsapp20.model.entities.eventEntity.Event
import com.hdceventsapp20.repository.eventRepository.EventRepository
import com.hdceventsapp20.ui.eventFragment.EventViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _allEventsState = MutableLiveData<List<Event>>()
    val allEventsState: LiveData<List<Event>>
        get() = _allEventsState

    private val _eventStateData = MutableLiveData<EventViewModel.EventState>()
    val eventStateData: LiveData<EventViewModel.EventState>
        get() = _eventStateData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun getEvents() = viewModelScope.launch {
        _allEventsState.postValue(eventRepository.getAll())
    }

    fun deleteMovie(id: Long) = viewModelScope.launch {
        try {
            eventRepository.delete(id)
            _eventStateData.value = EventViewModel.EventState.Deleted
            _messageEventData.value = R.string.success_deleted

        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    companion object {
        private val TAG = EventViewModel::class.java.simpleName
    }
}
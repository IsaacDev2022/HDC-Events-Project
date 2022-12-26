package com.hdceventsapp20.ui.expandedEventFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.hdceventsapp20.R
import com.hdceventsapp20.databinding.FragmentExpandedEventBinding
import com.hdceventsapp20.model.dao.eventDAO.EventDAO
import com.hdceventsapp20.model.db.AppDatabase
import com.hdceventsapp20.repository.eventRepository.EventDataSource
import com.hdceventsapp20.repository.eventRepository.EventRepository

class ExpandedEventFragment : Fragment() {

    private var _binding: FragmentExpandedEventBinding?= null
    private val binding get() = _binding!!

    private val args: ExpandedEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpandedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.event?.let { event ->
            binding.tvNameEvent.setText(event.nameEvent)
            binding.tvDateEvent.setText(event.dateEvent)
            binding.tvTimeEvent.setText(event.timeEvent)
            binding.tvCity.setText(event.city)
            binding.tvType.setText(event.type)
            binding.tvPrivateOrPublic.setText(event.privateOrPublic)
            binding.tvDescription.setText(event.description)
        }
    }
}
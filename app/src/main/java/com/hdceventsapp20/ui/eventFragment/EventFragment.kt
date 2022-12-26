package com.hdceventsapp20.ui.eventFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hdceventsapp20.R
import com.hdceventsapp20.databinding.FragmentEventBinding
import com.hdceventsapp20.model.dao.eventDAO.EventDAO
import com.hdceventsapp20.model.db.AppDatabase
import com.hdceventsapp20.repository.eventRepository.EventDataSource
import com.hdceventsapp20.repository.eventRepository.EventRepository

class EventFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelCLass: Class<T>): T {
                val eventDAO: EventDAO = AppDatabase.getInstance(requireContext()).eventDAO
                val eventRepository: EventRepository = EventDataSource(eventDAO)
                return EventViewModel(eventRepository) as T
            }
        }
    }

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    private val args: EventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.event?.let { event ->
            binding.buttonRegister.setText("Atualizar")
            binding.edNameEvent.setText(event.nameEvent)
            binding.edDateEvent.setText(event.dateEvent)
            binding.edTimeEvent.setText(event.timeEvent)
            binding.edCity.setText(event.city)
            binding.edType.setText(event.type)
            binding.edPrivateOrPublic.setText(event.privateOrPublic)
            binding.edDescription.setText(event.description)

            binding.buttonDelete.visibility = View.VISIBLE
        }

        observeEvents()
        insertEvent()
    }

    private fun observeEvents() {
        viewModel.eventStateData.observe(viewLifecycleOwner) { eventState ->
            when (eventState) {
                is EventViewModel.EventState.Inserted,
                is EventViewModel.EventState.Updated,
                is EventViewModel.EventState.Deleted -> {
                    findNavController().popBackStack()
                }
                else -> {}
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { string ->
            Snackbar.make(requireView(), string, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun insertEvent() {
        binding.buttonRegister.setOnClickListener {
            val nameEvent = binding.edNameEvent.text.toString()
            val dateEvent = binding.edDateEvent.text.toString()
            val timeEvent = binding.edTimeEvent.text.toString()
            val city = binding.edCity.text.toString()
            val type = binding.edType.text.toString()
            val privateOrPublic = binding.edPrivateOrPublic.text.toString()
            val description = binding.edDescription.text.toString()

            viewModel.addOrUpdate(
                nameEvent,
                dateEvent,
                timeEvent,
                city,
                type,
                privateOrPublic,
                description,
                args.event?.idEvent ?: 0
            )
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.removeEvent(args.event?.idEvent ?: 0)
        }
    }
}
package com.hdceventsapp20.ui.homeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hdceventsapp20.databinding.EventItemBinding
import com.hdceventsapp20.model.entities.eventEntity.Event

class EventListAdapter(
    private val event: List<Event>
) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    var onItemExpansiveClick: ((entity: Event) -> Unit)? = null
    var onItemUpdateClick: ((entity: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(event[position])
    }

    override fun getItemCount() = event.size

    inner class ViewHolder(
        private val binding: EventItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.tvEventName.text = event.nameEvent
            binding.tvEventDate.text = event.dateEvent
            binding.tvEventTime.text = event.timeEvent

            binding.btnExpansive.setOnClickListener {
                onItemExpansiveClick?.invoke(event)
            }

            binding.btnUpdate.setOnClickListener {
                onItemUpdateClick?.invoke(event)
            }
        }
    }
}
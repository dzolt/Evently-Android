package com.apusart.evently_android.logged.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.api.Event
import com.apusart.evently_android.R
import com.apusart.evently_android.logged.calendar.CalendarFragmentDirections
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.event_list_item.view.*

class EventsAdapter: ListAdapter<Event, EventViewHolder>(diffUtil) {

    object diffUtil: DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)

        return EventViewHolder(container)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class EventViewHolder(container: View): RecyclerView.ViewHolder(container) {
    fun bind(event: Event) {
        itemView.apply {
            event_list_item_event_date.text = event.date
            event_list_item_event_title.text = event.name
            event_list_item_event_host.text = event.creator.name
            event_list_item_event_location.text = event.place
            event_list_item_event_participants.listOfParticipants = event.joinedUsers
            Glide.with(this)
                .load(if(event.photoPath == "") R.drawable.add_picture else event.photoPath)
                .into(event_list_item_image)

            setOnClickListener {
                findNavController()
                    .navigate(
                        EventsFragmentDirections.actionEventsFragmentToEventDetailsHomeFragment(event.id))
            }
        }
    }
}
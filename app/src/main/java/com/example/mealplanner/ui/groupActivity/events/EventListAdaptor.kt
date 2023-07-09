package com.example.mealplanner.ui.groupActivity.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R


class EventListAdaptor(private val onClick: (Event) -> Unit) :
    ListAdapter<Event, EventListAdaptor.ViewHolder>(DiffCallback) {
    // ViewHolder is a container for an item in the view
    class ViewHolder(itemView: View, private val onClick: (Event) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.event_name)
        private val timeTextView = itemView.findViewById<TextView>(R.id.event_time)
        private val locationTextView = itemView.findViewById<TextView>(R.id.event_location)

        private val eventImageView = itemView.findViewById<ImageView>(R.id.event_image)
        private val imageButton = itemView.findViewById<ImageButton>(R.id.select_event_button)
        private var currentEvent: Event? = null

        init {
            imageButton.setOnClickListener {
                currentEvent?.let {
                    onClick(it)
                }
            }
        }

        // function called to update the content of a list
        fun bind(event: Event) {
            currentEvent = event
            nameTextView.text = event.name
            timeTextView.text = event.time
            locationTextView.text = event.location
        }
    }

    // Called when a new item is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view, onClick)
    }

    // Called to update an item given an index
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }
}

object DiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}
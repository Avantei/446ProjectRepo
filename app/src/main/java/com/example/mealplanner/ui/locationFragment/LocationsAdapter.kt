package com.example.mealplanner.ui.locationFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R

class LocationsAdapter(private val locationsList: MutableList<LocationSuggestion>) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),  View.OnClickListener{
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView: TextView = itemView.findViewById(R.id.list_item_text)
        val messageButton: Button = itemView.findViewById(R.id.list_item_button)
        init {
            // Attach a click listener to the entire row view
            messageButton.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                locationsList[position].votes += 1
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.location_list_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: LocationsAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val location: LocationSuggestion = locationsList.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        textView.text = location.name
        val button = viewHolder.messageButton
        button.text = location.votes.toString()
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return locationsList.size
    }
}
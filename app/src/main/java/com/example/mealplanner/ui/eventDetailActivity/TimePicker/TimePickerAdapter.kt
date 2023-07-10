package com.example.mealplanner.ui.eventDetailActivity.TimePicker

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.ui.eventDetailActivity.EventDetailViewModel
import com.example.mealplanner.ui.locationFragment.LocationSuggestion
import com.example.mealplanner.ui.eventDetailActivity.TimePicker.TimePickerData


class TimePickerAdapter(private var availabilitiesList: List<TimePickerData>) :
    RecyclerView.Adapter<TimePickerAdapter.ViewHolder>() {

//    private var availabilitiesList: List<TimePickerData> = viewModel.availabilitiesList.value ?: listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: Button = itemView.findViewById(R.id.timeTextView)

        init {
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimePickerAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.fragment_time_picker, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: TimePickerAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val data: TimePickerData = availabilitiesList[position]
        // Set item views based on your views and data model
        val textView = viewHolder.dateTextView
        textView.text = data.date
        val textView2 = viewHolder.timeTextView
        textView2.text = data.time
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return availabilitiesList.size
    }

    fun updateAdapter(newList: List<TimePickerData>) {
        availabilitiesList = newList
        notifyDataSetChanged()
    }
}
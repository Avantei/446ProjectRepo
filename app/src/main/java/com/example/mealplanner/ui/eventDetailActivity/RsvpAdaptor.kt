package com.example.mealplanner.ui.eventDetailActivity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentRsvpBinding

class RsvpListAdapter(private val onButtonClick: (RsvpGroupMember, String) -> Unit, private val onCheckBoxClick: (RsvpGroupMember) -> Unit) :
    ListAdapter<RsvpGroupMember, RsvpListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(itemView: View, private val onButtonClick: (RsvpGroupMember, String) -> Unit, private val onCheckBoxClick: (RsvpGroupMember) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.itemRsvpName)
        private val subTextView = itemView.findViewById<TextView>(R.id.itemRsvpSubText)
        private val button = itemView.findViewById<Button>(R.id.itemRsvpButton)
        private val isAttendingCheckBox = itemView.findViewById<CheckBox>(R.id.itemRsvpCheckBox)
        private var currentRsvpGroupMember: RsvpGroupMember? = null

        init {
            button.setOnClickListener {
                currentRsvpGroupMember?.let {
                    onButtonClick(it, super@RsvpListAdapter.getItem(0).name) //handles data changes
                    bind(it) //handles all UI changes for new data
                }
            }
            isAttendingCheckBox.setOnClickListener {
                currentRsvpGroupMember?.let {
                    onCheckBoxClick(it) //handles data changes
                    bind(it) //handles all UI changes for new data
                }
            }
        }

        fun bind(rsvpGroupMember: RsvpGroupMember) {
            //ASSUMPTION: THE CURRENT USER OF THE APP WILL ALWAYS BE THE FIRST ELEMENT OF THE RsvpGroupMember LIST
            currentRsvpGroupMember = rsvpGroupMember
            val userRsvpGroupMember = super@RsvpListAdapter.getItem(0) //we know there must be at least one rsvpGroupMember
            val listItemRepresentsCurrentUser = (rsvpGroupMember.name == userRsvpGroupMember.name)

            //TODO: finish updating all the values relating to the UI state
            nameTextView.text = rsvpGroupMember.name
            isAttendingCheckBox.isChecked = rsvpGroupMember.isAttending

            if (!isAttendingCheckBox.isChecked) {
                //if not attending
                subTextView.text = "N/A"
                subTextView.setTextColor(Color.GRAY)
                button.isVisible = false;
            } else {
                //if member is attending
                if (rsvpGroupMember.transport == Transport.DOES_NEED_RIDE) {
                    //if member does need a ride
                    subTextView.text = "Need a Ride"
                    subTextView.setTextColor(Color.RED)
                    button.text = ("Give Ride")
                    button.isVisible = userRsvpGroupMember.isAttending && userRsvpGroupMember.transport == Transport.DOES_NOT_NEED_RIDE
                } else {
                    //either don't need a ride or riding with someone
                    Log.d("TODO", "bind() setting ride not needed")
                    if (rsvpGroupMember.ridingWith.isNotEmpty()) {
                        subTextView.text = "Riding with ${rsvpGroupMember.ridingWith}"
                        subTextView.setTextColor(Color.BLUE)
                        button.isVisible = false;
                        Log.d("TODO", "bind() setting riding with")

                        if (rsvpGroupMember.ridingWith == userRsvpGroupMember.name) {
                            button.text = "Rescind Ride"
                            button.isVisible = true;
                        }
                    } else {
                        subTextView.text = "Don't Need a Ride"
                        subTextView.setTextColor(Color.GREEN)
                        button.isVisible = false;
                    }
                }
            }
            if (listItemRepresentsCurrentUser) {
                isAttendingCheckBox.isEnabled = true;
                if (rsvpGroupMember.transport == Transport.DOES_NOT_NEED_RIDE) {
                    button.text = "Need Ride"
                } else {
                    button.text = "Got Ride"
                }
                itemView.setBackgroundColor(Color.parseColor("#EEEEEE"))
                button.isVisible = rsvpGroupMember.isAttending
            } else {
                isAttendingCheckBox.isEnabled = false;
                itemView.setBackgroundColor(Color.WHITE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rsvp, parent, false)
        return ViewHolder(view, onButtonClick, onCheckBoxClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val RsvpGroupMember = getItem(position)
        holder.bind(RsvpGroupMember)
    }
}

object DiffCallback : DiffUtil.ItemCallback<RsvpGroupMember>() {
    override fun areItemsTheSame(oldItem: RsvpGroupMember, newItem: RsvpGroupMember): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RsvpGroupMember, newItem: RsvpGroupMember): Boolean {
        return oldItem.name == newItem.name
                && oldItem.isAttending == newItem.isAttending
                && oldItem.transport == newItem.transport
                && oldItem.ridingWith == newItem.ridingWith;
    }
}
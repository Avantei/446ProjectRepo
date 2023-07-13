package com.example.mealplanner.ui.eventDetailActivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentRsvpBinding

class RsvpListAdapter(private val onButtonClick: (RsvpGroupMember) -> Unit, private val onCheckBoxClick: (RsvpGroupMember) -> Unit) :
    ListAdapter<RsvpGroupMember, RsvpListAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(itemView: View, private val onButtonClick: (RsvpGroupMember) -> Unit, private val onCheckBoxClick: (RsvpGroupMember) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.itemRsvpName)
        private val button = itemView.findViewById<Button>(R.id.itemRsvpButton)
        private val isAttendingCheckBox = itemView.findViewById<CheckBox>(R.id.itemRsvpCheckBox)
        private var currentRsvpGroupMember: RsvpGroupMember? = null

        init {
            button.setOnClickListener {
                currentRsvpGroupMember?.let {
                    onButtonClick(it)
                }
            }
            isAttendingCheckBox.setOnClickListener {
                currentRsvpGroupMember?.let {
                    onCheckBoxClick(it)
                }
            }
        }

        fun bind(RsvpGroupMember: RsvpGroupMember) {
            currentRsvpGroupMember = RsvpGroupMember
            nameTextView.text = RsvpGroupMember.name
            //need to update other values too...
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
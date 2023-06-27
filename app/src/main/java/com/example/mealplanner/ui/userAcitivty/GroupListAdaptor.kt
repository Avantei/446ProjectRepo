package com.example.mealplanner.ui.userAcitivty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R


class GroupListAdaptor(private val onClick: (Group) -> Unit) :
    ListAdapter<Group, GroupListAdaptor.ViewHolder>(DiffCallback) {
    // ViewHolder is a container for an item in the view
    class ViewHolder(itemView: View, private val onClick: (Group) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.textView)
        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        private var currentGroup: Group? = null

        init {
            imageButton.setOnClickListener {
                currentGroup?.let {
                    onClick(it)
                }
            }
        }

        // function called to update the content of a list
        fun bind(group: Group) {
            currentGroup = group
            nameTextView.text = group.name
        }
    }

    // Called when a new item is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_groups, parent, false)
        return ViewHolder(view, onClick)
    }

    // Called to update an item given an index
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }
}

object DiffCallback : DiffUtil.ItemCallback<Group>() {
    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem == newItem
    }
}
package com.example.mealplanner.ui.groupActivity.members

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


class MemberListAdaptor(private val onClick: (String) -> Unit) :
    ListAdapter<String, MemberListAdaptor.ViewHolder>(DiffCallback) {
    // ViewHolder is a container for an item in the view
    class ViewHolder(itemView: View, private val onClick: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val groupMemberTextView = itemView.findViewById<TextView>(R.id.group_member_name)

        private val selectMemberButton = itemView.findViewById<ImageButton>(R.id.select_group_member)

        init {
            selectMemberButton.setOnClickListener {
                onClick(groupMemberTextView.text.toString())
            }
        }

        // function called to update the content of a list
        fun bind(memberName: String) {
            print("BIND UPDATE")
            groupMemberTextView.text = memberName
        }
    }

    // Called when a new item is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_member_item, parent, false)
        return ViewHolder(view, onClick)
    }

    // Called to update an item given an index
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }
}

object DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
package com.example.mealplanner.ui.rsvp

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
import com.example.mealplanner.ui.user.UserViewModel

enum class Transport {
    NOT_APPLICABLE,
    DOES_NOT_NEED_RIDE,
    DOES_NEED_RIDE,
    CAN_PROVIDE_RIDE
}

data class GroupMember(
  var name: String,
  var isAttending: Boolean,
  var transport: Transport,
  var ridingWith: GroupMember? = null,
)

val GROUP_MEMBERS = listOf(
    GroupMember("Matt", false, Transport.NOT_APPLICABLE, null),
    GroupMember("Max", false, Transport.NOT_APPLICABLE, null),
    GroupMember("Marwan", false, Transport.NOT_APPLICABLE, null),
    GroupMember("Peter", false, Transport.NOT_APPLICABLE, null),
    GroupMember("Jas", false, Transport.NOT_APPLICABLE, null),
    GroupMember("Justin", false, Transport.NOT_APPLICABLE, null),
)

class RsvpFragment : Fragment() {
    private var _binding: FragmentRsvpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve viewModel from parent activity
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRsvpBinding.inflate(inflater, container, false)
        val root = binding.root

        val recyclerView = binding.groupMembersList
        val recyclerAdapter = RsvpListAdapter(
            {Log.d("TODO", "Button Click for group member ${it.name}")},
            {Log.d("TODO", "CheckBox Click for group member ${it.name}")}
        )

        recyclerAdapter.submitList(GROUP_MEMBERS)
        recyclerView.adapter = recyclerAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class RsvpListAdapter(private val onButtonClick: (GroupMember) -> Unit, private val onCheckBoxClick: (GroupMember) -> Unit) :
        ListAdapter<GroupMember, RsvpListAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(itemView: View, private val onButtonClick: (GroupMember) -> Unit, onCheckBoxClick: (GroupMember) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.itemRsvpName)
        private val button = itemView.findViewById<Button>(R.id.itemRsvpButton)
        private val isAttendingCheckBox = itemView.findViewById<CheckBox>(R.id.itemRsvpCheckBox)
        private var currentGroupMember: GroupMember? = null

        init {
            button.setOnClickListener {
                currentGroupMember?.let {
                    onButtonClick(it)
                }
            }
            isAttendingCheckBox.setOnClickListener {
                currentGroupMember?.let {
                    onCheckBoxClick(it)
                }
            }
        }

        fun bind(groupMember: GroupMember) {
            currentGroupMember = groupMember
            nameTextView.text = groupMember.name
            //need to update other values too...
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rsvp, parent, false)
        return ViewHolder(view, onButtonClick, onCheckBoxClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupMember = getItem(position)
        holder.bind(groupMember)
    }
}

object DiffCallback : DiffUtil.ItemCallback<GroupMember>() {
    override fun areItemsTheSame(oldItem: GroupMember, newItem: GroupMember): Boolean {
        return oldItem.name == newItem.name
                && oldItem.isAttending == newItem.isAttending
                && oldItem.transport == newItem.transport
                && oldItem.ridingWith == newItem.ridingWith;
    }

    override fun areContentsTheSame(oldItem: GroupMember, newItem: GroupMember): Boolean {
        return oldItem == newItem
    }
}
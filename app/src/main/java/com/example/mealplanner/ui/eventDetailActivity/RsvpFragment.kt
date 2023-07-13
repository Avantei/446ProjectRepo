package com.example.mealplanner.ui.eventDetailActivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.databinding.FragmentRsvpBinding
import com.example.mealplanner.ui.eventDetailActivity.EventDetailViewModel
import com.example.mealplanner.ui.groupActivity.events.EventListAdaptor

class RsvpFragment : Fragment() {
    /*
        States:
        1. Not Attending
            - Subtext: "N/A"
            - Button not displayed


        + Extra: need to know the current app user to shade their box
     */

    private var _binding: FragmentRsvpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var rsvpGroupMembers: List<RsvpGroupMember>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val eventDetailViewModel =
            ViewModelProvider(this).get(EventDetailViewModel::class.java)

        _binding = FragmentRsvpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerView
        val recyclerAdaptor = RsvpListAdapter(
            ::buttonClickHandler,
            ::isAttendingCheckboxClickHandler
        )

        eventDetailViewModel.rsvpGroupMembers.observe(viewLifecycleOwner) {
            recyclerAdaptor.submitList(it.toList())
            this.rsvpGroupMembers = it.toList()
        }

        recyclerView.adapter = recyclerAdaptor

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isAttendingCheckboxClickHandler(rsvpGroupMember: RsvpGroupMember) {
        rsvpGroupMember.isAttending = !rsvpGroupMember.isAttending

        // note that this event is only allowed for the current user
        // case when we offered a ride, but now are saying that we're not attending, before rescinding our offer
        for (member in this.rsvpGroupMembers) {
            if (member.ridingWith == rsvpGroupMember.name) {
                member.ridingWith = ""
                member.needsRide = true
            }
        }
        recyclerView.adapter!!.notifyDataSetChanged()
        Log.d("TODO", "CheckBox Click for group member ${rsvpGroupMember.name}. new value: ${rsvpGroupMember.isAttending}")
    }

    fun buttonClickHandler(rsvpGroupMember: RsvpGroupMember, currentUserName: String) {
        val isCurrentUserButtonClick = (rsvpGroupMember.name == currentUserName)
        if (isCurrentUserButtonClick) {
            //"need ride" or "got ride" button clicked => toggle transport
            rsvpGroupMember.needsRide = !rsvpGroupMember.needsRide

            // only if the button we're clicking is the current user
            // case when we offered a ride, but now are saying that we ourselves need a ride before rescinding our offer
            for (member in this.rsvpGroupMembers) {
                if (member.ridingWith == currentUserName) {
                    member.ridingWith = ""
                    member.needsRide = true
                }
            }
            recyclerView.adapter!!.notifyDataSetChanged()
        } else {
            //user is clicking a 'give ride' button on another group member (rsvpGroupMember is the other group member)
            // or rescinding a given ride
            if (rsvpGroupMember.ridingWith == currentUserName) {
                //we're now rescinding an offered ride
                rsvpGroupMember.needsRide = true
                rsvpGroupMember.ridingWith = ""
            } else {
                rsvpGroupMember.needsRide = false
                rsvpGroupMember.ridingWith = currentUserName
            }
        }
        Log.d("TODO", "Button Click for group member ${rsvpGroupMember.name}")
    }
}
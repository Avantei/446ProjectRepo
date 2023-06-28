package com.example.mealplanner.ui.groupActivity.events

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentGroupEventsBinding
import com.example.mealplanner.ui.createEventActivity.CreateEventActivity
import com.example.mealplanner.ui.userActivity.UserActivity

class EventsFragment : Fragment() {

    private var _binding: FragmentGroupEventsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val eventsViewModel =
            ViewModelProvider(this).get(EventsViewModel::class.java)

        _binding = FragmentGroupEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        val recyclerAdaptor = EventListAdaptor {
            Log.d("TODO", "EVENT CLICKED - NAME: ${it.name}")
        }

        eventsViewModel.events.observe(viewLifecycleOwner) {
            recyclerAdaptor.submitList(it.toList())
        }

        recyclerView.adapter = recyclerAdaptor

        val inputtedEventName = binding.inputEventName
        binding.addEventButton.setOnClickListener {
            val newEventName = inputtedEventName.text.toString()
            if (newEventName.isNotEmpty()) {
                //eventsViewModel.addEvent(newEventName)
                val intent = Intent(activity, CreateEventActivity::class.java)
                intent.putExtra("EventName", newEventName)
                startActivity(intent)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
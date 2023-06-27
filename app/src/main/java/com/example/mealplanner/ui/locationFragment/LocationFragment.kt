package com.example.mealplanner.ui.locationFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentLocationBinding
import com.example.mealplanner.ui.eventDetailActivity.EventDetailViewModel


class LocationFragment : Fragment() {

    private lateinit var viewModel: EventDetailViewModel
    private lateinit var binding: FragmentLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        val text = binding.editTextText
        val button = binding.button
        val location = binding.rvLocation

        val adapter = viewModel.locationList.value?.let { LocationsAdapter(it) }
        button.setOnClickListener {
            viewModel.locationList.value?.add(0, LocationSuggestion(text.text.toString(), 0))
            adapter?.notifyItemInserted(0)
        }
        location.adapter = adapter
        // Set layout manager to position the items
        location.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EventDetailViewModel::class.java]
    }
}
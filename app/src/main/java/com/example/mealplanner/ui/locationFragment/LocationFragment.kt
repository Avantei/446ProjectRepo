package com.example.mealplanner.ui.locationFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        val adapter = LocationsAdapter(viewModel)
        button.setOnClickListener {
            viewModel.addLocation(text.text.toString())
        }
        location.adapter = adapter
        // Set layout manager to position the items
        location.layoutManager = LinearLayoutManager(activity)

        viewModel.locationList.observe(viewLifecycleOwner) {
            adapter.updateAdapter()
        }

        // TODO: for demo only, removed after
        viewModel.addLocation("Pizza")
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EventDetailViewModel::class.java]
    }
}
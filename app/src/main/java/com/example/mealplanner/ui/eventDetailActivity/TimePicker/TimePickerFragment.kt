package com.example.mealplanner.ui.eventDetailActivity.TimePicker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentTimePickerBinding
import com.example.mealplanner.ui.eventDetailActivity.EventDetailViewModel

class TimePickerFragment : Fragment() {

    private lateinit var viewModel: EventDetailViewModel
    private lateinit var binding: FragmentTimePickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimePickerBinding.inflate(layoutInflater)
        val date = binding.dateAvailability
        val time = binding.timeAvailability
        val addButton = binding.addButton
        val submitButton = binding.submitButton
        val availabilities = binding.rvAvailabilities

        val adapter = TimePickerAdapter(viewModel.availabilitiesList.value ?: emptyList())

        addButton.setOnClickListener {
            val dateText = date.text.toString()
            val timeText = time.text.toString()
            if (dateText.isNotEmpty() && timeText.isNotEmpty()) {
                viewModel.addAvailability(dateText, timeText)
                adapter.updateAdapter(viewModel.availabilitiesList.value ?: emptyList())
                date.text.clear()
                time.text.clear()
            }
        }

        submitButton.setOnClickListener {
            viewModel.submitAvailability()
        }

        availabilities.adapter = adapter
        availabilities.layoutManager = LinearLayoutManager(activity)

        viewModel.availabilitiesList.observe(viewLifecycleOwner) { newList ->
            Log.d("ViewModel", "availabilitiesList updated: $newList")
            adapter.updateAdapter(newList ?: emptyList())
        }

//        // TODO: for demo only, removed after
//        viewModel.addAvailability("01/01/2023", "12:30-15:30")

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EventDetailViewModel::class.java]
    }

    fun onAddButtonClick() {
        //addRow()
    }

    fun onSaveButtonClick() {
        //saveData()
    }

    fun onDateButtonClick(row: TableRow) {
        // Handle date button click
    }

    fun onTimeButtonClick(row: TableRow) {
        // Handle time button click
    }

}
package com.example.mealplanner.ui.eventDetailActivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [TimePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimePickerFragment : Fragment() {
    private lateinit var viewModel: EventDetailViewModel
    private lateinit var timePicker: TimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EventDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_time_picker, container, false)
        val button = view.findViewById<Button>(R.id.submitButton)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val startTime = view.findViewById<TimePicker>(R.id.startTime)
        val endTime = view.findViewById<TimePicker>(R.id.endTime)
        var selectedDate = getDateFromTimestamp(calendarView.date)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = getDate(year, month, dayOfMonth)
        }

        button.setOnClickListener {
            viewModel.setAvailableDate(selectedDate)
            viewModel.setAvailableStartTime(getTimeFromTimePicker(startTime.hour, startTime.minute))
            viewModel.setAvailableEndTime(getTimeFromTimePicker(endTime.hour, endTime.minute))
        }
        return view
    }

    private fun getDate(year: Int, month: Int, dayOfMonth: Int): String {
        // Customize the date format as per your requirements
        val formattedMonth = (month + 1).toString().padStart(2, '0')
        val formattedDay = dayOfMonth.toString().padStart(2, '0')
        return "$formattedDay/$formattedMonth/$year"
    }

    private fun getDateFromTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    private fun getTimeFromTimePicker(hour : Int, minute : Int): String {
        val formattedHour = String.format("%02d", hour)
        val formattedMinute = String.format("%02d", minute)

        return "$formattedHour:$formattedMinute"
    }
}
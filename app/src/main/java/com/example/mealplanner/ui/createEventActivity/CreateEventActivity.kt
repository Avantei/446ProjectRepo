package com.example.mealplanner.ui.createEventActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.text.set
import com.example.mealplanner.R
import com.example.mealplanner.databinding.ActivityCreateEventBinding
import com.example.mealplanner.databinding.ActivityGroupBinding
import com.example.mealplanner.ui.eventDetailActivity.EventActivity

class CreateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newEventName = intent.getStringExtra("EventName")

        val newEventTextBox = findViewById<EditText>(R.id.new_event_name)
        newEventTextBox.setText(newEventName)

        val locationRadioGroup = findViewById<RadioGroup>(R.id.locationRadioGroup)
        val timeRadioGroup = findViewById<RadioGroup>(R.id.timeRadioGroup)
        val eventOrderRadioGroup = findViewById<RadioGroup>(R.id.eventFirstOrder)

        binding.createEventButton.setOnClickListener {
            if (locationRadioGroup.checkedRadioButtonId != -1 &&
                timeRadioGroup.checkedRadioButtonId != -1 &&
                eventOrderRadioGroup.checkedRadioButtonId != -1) {
                val intent = Intent(this, EventActivity::class.java)
                //any put extra pass calls here
                startActivity(intent)
            }
        }
    }
}
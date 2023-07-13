package com.example.mealplanner.ui.eventDetailActivity

data class RsvpGroupMember(
    var name: String,
    var isAttending: Boolean,
    var needsRide: Boolean,
    var ridingWith: String,
)
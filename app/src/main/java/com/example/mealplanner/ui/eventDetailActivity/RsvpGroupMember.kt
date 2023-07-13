package com.example.mealplanner.ui.eventDetailActivity

enum class Transport {
    NOT_APPLICABLE,
    DOES_NOT_NEED_RIDE,
    DOES_NEED_RIDE,
    CAN_PROVIDE_RIDE
}

data class RsvpGroupMember(
    var name: String,
    var isAttending: Boolean,
    var transport: Transport,
    var ridingWith: RsvpGroupMember? = null,
)
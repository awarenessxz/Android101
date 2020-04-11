package com.learning.android101.models

import org.joda.time.DateTime

data class CalendarDate(
    val display: String,
    val datetime: DateTime,
    val isThisMonth: Boolean,
    val isSunday: Boolean,
    val isToday: Boolean
)

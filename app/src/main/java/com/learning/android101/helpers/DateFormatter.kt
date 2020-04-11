package com.learning.android101.helpers

import java.text.SimpleDateFormat               // formatting date object
import java.util.*                              // Calendar, Date
import org.joda.time.DateTime                   // DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.format.DateTimeFormat      // formating joda datetime object
import org.joda.time.LocalDate

object DateFormatter {
    const val DAYCODE_PATTERN = "YYYYMMdd"

    const val DATE_PATTERN = "dd-MM-yyyy"
    const val DATE_PATTERN_V2 = "dd MMM yy"
    const val DATETIME_PATTERN = "dd-MM-yyyy hh:mm a"
    const val DATETIME_12_HOUR_PATTERN = "hh a"
    const val DATETIME_24_HOUR_PATTERN = "HH"

    /************************************************************************************
     * Functions to get current Date / Datetime
     ************************************************************************************/

    // Get current date (Date Object)
    fun getCurrentDate() : Date {
        return Date()
    }

    // get current date in dd-MM-yyyy string format
    fun getCurrentDateString() : String {
        val dateFormatte = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        val dateStr = dateFormatte.format(Date())
        return dateStr
    }

    // Get current date time (DateTime Object)
    fun getCurrentDateTime() : DateTime {
        return DateTime()
    }

    // Get current DateTime in dd-MM-yyyy hh:mm a string format
    fun getCurrentDateTimeString() : String {
        val dateFormatte = SimpleDateFormat(DATETIME_PATTERN, Locale.getDefault())
        val dateStr = dateFormatte.format(Date())
        return dateStr
    }

    /************************************************************************************
     * Functions that converts datetime into string format (vice versa)
     ************************************************************************************/

    /********* DATE ***********/

    // Convert dd-MM-yyyy into Date object
    fun getDateObjFromDateString(dateString: String) : Date {
        val dateFormatte = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        val dateObj = dateFormatte.parse(dateString)!!
        return dateObj
    }

    // Convert Date Object into dd-MM-yyyy format
    fun getDateStringFromDateObj(date: Date) : String {
        val dateFormatte = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        val dateStr = dateFormatte.format(date)
        return dateStr
    }

    /********* DATETIME ***********/

    // Convert DateTime object into dd-MM-yyyy format
    fun getDateStringFromDateTimeObj(datetime: DateTime) : String {
        val dtf = DateTimeFormat.forPattern(DATE_PATTERN)
        val dateStr = dtf.print(datetime)
        return dateStr
    }

    // Convert DateTime object into dd-MM-yyyy hh:mm a format
    fun getDateTimeStringFromDateTimeObj(datetime: DateTime) : String {
        val dtf = DateTimeFormat.forPattern(DATETIME_PATTERN)
        val datetimeStr = dtf.print(datetime)
        return datetimeStr
    }

    // Convert dd-MM-yyyy into DateTime object
    fun getDateTimeObjFromDateStr(dateStr: String) : DateTime {
        val dtf = DateTimeFormat.forPattern(DATE_PATTERN)
        val datetime = dtf.parseDateTime(dateStr)
        return datetime
    }

    // Convert dd-MM-yyyy hh:mm a into DateTime object
    fun getDateTimeObjFromDateTimeStr(datetimeStr: String) : DateTime {
        val dtf = DateTimeFormat.forPattern(DATETIME_PATTERN)
        val datetime = dtf.parseDateTime(datetimeStr)
        return datetime
    }

    /************************************************************************************
     * Functions to create date time
     ************************************************************************************/

    // Convert Year, Month, Day into a DateTime Object
    fun getDateTimeObjFromYearMonthDay(year: Int, month: Int, day: Int) : DateTime {
        val datetime = DateTime()
        val newDateTime = datetime.withDate(year, month, day)
        return newDateTime
    }

    // Convert Year, Month, Day into a dd-MM-yyyy format (Note: Month = 1-12)
    fun getDateStrFromYearMonthDay(year: Int, month: Int, day: Int) : String {
        val datetime = getDateTimeObjFromYearMonthDay(year, month, day)
        val dateStr = getDateStringFromDateTimeObj(datetime)
        return dateStr
    }

    /************************************************************************************
     * Functions to parse and format datetime object
     ************************************************************************************/

    fun getHoursIn12HoursFormat(datetime: DateTime) : String {
        val dtf = DateTimeFormat.forPattern(DATETIME_12_HOUR_PATTERN)
        val hours = dtf.print(datetime)
        return hours
    }

    fun getHoursIn24HoursFormat(datetime: DateTime) : String {
        val dtf = DateTimeFormat.forPattern(DATETIME_24_HOUR_PATTERN)
        val hours = dtf.print(datetime)
        return hours
    }

    /************************************************************************************
     * Functions to extract information / manipulate date String
     ************************************************************************************/

    // get DateTime Object for the first day of the week (Monday...Sunday = 1..7)
    fun getDateTimeForFirstDayOfWeek(dateStr: String, isSundayFirst: Boolean) : DateTime {
        val datetime = getDateTimeObjFromDateStr(dateStr)
        val weekIndex = datetime.dayOfWeek
        if (isSundayFirst) {
            return datetime.plusDays(-(weekIndex % 7))
        } else {
            return datetime.plusDays(-(weekIndex-1))
        }
    }

    fun getMonthTextAndYearFromDateStr(dateStr: String) : String {
        val datetime = getDateTimeObjFromDateStr(dateStr)
        return "${datetime.monthOfYear().asText} ${datetime.year}"
    }

    fun formatDateStr(dateStr: String) : String {
        val datetime = getDateTimeObjFromDateStr(dateStr)
        val dtf = DateTimeFormat.forPattern(DATE_PATTERN_V2)
        val formattedDate = dtf.print(datetime)
        return formattedDate
    }

    /************************************************************************************
     * Functions to extract information / manipulate datetime object
     ************************************************************************************/

    fun isDateTimeToday(datetime: DateTime) : Boolean {
        return LocalDate.now().equals(LocalDate(datetime));
    }

    fun isDateTimeYesterday(datetime: DateTime) : Boolean {
        return LocalDate.now().minusDays(1).equals(LocalDate(datetime));
    }

    fun isDateTimeTomorrow(datetime: DateTime) : Boolean {
        return LocalDate.now().plusDays(1).equals(LocalDate(datetime));
    }

    fun isDateTimeWeekend(datetime: DateTime) : Boolean {
        return (datetime.dayOfWeek == DateTimeConstants.SATURDAY || datetime.dayOfWeek == DateTimeConstants.SUNDAY)
    }

    fun isDateTimeSunday(datetime: DateTime) : Boolean {
        return (datetime.dayOfWeek == DateTimeConstants.SUNDAY)
    }

    fun isDateTimeCurMonth(datetime: DateTime) : Boolean {
        val now = DateTime()
        return (datetime.monthOfYear == now.monthOfYear && datetime.year == now.year)
    }

    /************************************************************************************
     * Functions to manipulate date object
     ************************************************************************************/

    // add/minus x day from date Obj -- return date obj
    fun addXDaysToDate(date: Date, x: Int) : Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_MONTH, x)
        val newDate = cal.time
        return newDate
    }

    // add/minus x day from date str -- return date str
    fun addXDaysToDateStr(dateStr: String, x: Int) : String {
        val date = getDateObjFromDateString(dateStr)
        val newDate = addXDaysToDate(date,x)
        val newDateStr = getDateStringFromDateObj(newDate)
        return newDateStr
    }

    // add/minus x month from date Obj -- return date obj
    fun addXMonthsToDate(date: Date, x: Int) : Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, x)
        val newDate = cal.time
        return newDate
    }

    // add/minus x month from date str -- return date str
    fun addXMonthsToDateStr(dateStr: String, x: Int) : String {
        val date = getDateObjFromDateString(dateStr)
        val newDate = addXMonthsToDate(date,x)
        val newDateStr = getDateStringFromDateObj(newDate)
        return newDateStr
    }

    /************************************************************************************
     * Calendar
     ************************************************************************************/

    // get day from date given (DD-MM-YYYY) --> returns DD
    fun getDayFromDateStr(dateStr: String) : Int {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        return cal.get(Calendar.DAY_OF_MONTH)
    }

    // get first date in the week of the date given (Monday...Sunday = 1..7)
    fun getFirstDateOfWeekFromDateStr(dateStr: String, isSundayFirst: Boolean): String {
        val weekIndex = getWeekIndexFromDateStr(dateStr)
        if (isSundayFirst) {
            return addXDaysToDateStr(dateStr, -(weekIndex % 7))
        } else {
            return addXDaysToDateStr(dateStr, -(weekIndex-1))
        }
    }

    // get first date in the month of the date given (DD-MM-YYYY) --> returns 01-MM-YYYY
    fun getFirstDateOfMonthFromDateStr(dateStr: String): String {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        cal.set(Calendar.DAY_OF_MONTH, 1)
        return getDateStringFromDateObj(cal.time)
    }

    // get the index (of the week) from date given (Monday...Sunday = 1..7)
    fun getWeekIndexFromDateStr(dateStr: String) : Int {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        // compute
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> -1
        }
    }

    // get the week number of year from date given
    fun getWeekNumOfYearFromDateStr(dateStr: String) : Int {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        return cal.get(Calendar.WEEK_OF_YEAR)
    }

    // get month from date given (DD-MM-YYYY) --> returns MM (0-11)
    fun getMonthFromDateStr(dateStr: String): Int {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        return cal.get(Calendar.MONTH)
    }

    // get month label from date given (DD-MM-YYYY) --> returns MMM (January...December)
    fun getMonthLabelFromDateStr(dateStr: String): String {
        val monthIdx = getMonthFromDateStr(dateStr)
        return when (monthIdx) {
            Calendar.JANUARY -> "January"
            Calendar.FEBRUARY -> "February"
            Calendar.MARCH -> "March"
            Calendar.APRIL -> "April"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "June"
            Calendar.JULY -> "July"
            Calendar.AUGUST -> "August"
            Calendar.SEPTEMBER -> "September"
            Calendar.OCTOBER -> "October"
            Calendar.NOVEMBER -> "November"
            Calendar.DECEMBER -> "December"
            else -> ""
        }
    }

    // get month label and year from date given (DD-MM-YYYY) --> returns MMM-YYYY
    fun getMonthYearFromDateStr(dateStr: String): String {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        val year = cal.get(Calendar.YEAR)
        return when (cal.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "January $year"
            Calendar.FEBRUARY -> "February $year"
            Calendar.MARCH -> "March $year"
            Calendar.APRIL -> "April $year"
            Calendar.MAY -> "May $year"
            Calendar.JUNE -> "June $year"
            Calendar.JULY -> "July $year"
            Calendar.AUGUST -> "August $year"
            Calendar.SEPTEMBER -> "September $year"
            Calendar.OCTOBER -> "October $year"
            Calendar.NOVEMBER -> "November $year"
            Calendar.DECEMBER -> "December $year"
            else -> ""
        }
    }

    // get month label, year and week from date given (DD-MM-YYYY) --> returns (MMM, YY, Week Number)
    fun getMonthYearWeekFromDateStr(dateStr: String, isSundayFirst: Boolean): Triple<String, Int, Int> {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        if (isSundayFirst) {
            cal.firstDayOfWeek = Calendar.SUNDAY
        } else {
            cal.firstDayOfWeek = Calendar.MONDAY
        }
        // Get Week Number
        val weekNum = cal.get(Calendar.WEEK_OF_YEAR)
        val year = cal.get(Calendar.YEAR)
        return when (cal.get(Calendar.MONTH)) {
            Calendar.JANUARY -> Triple("Jan", year, weekNum)
            Calendar.FEBRUARY -> Triple("Feb", year, weekNum)
            Calendar.MARCH -> Triple("Mar", year, weekNum)
            Calendar.APRIL -> Triple("Apr", year, weekNum)
            Calendar.MAY -> Triple("May", year, weekNum)
            Calendar.JUNE -> Triple("Jun", year, weekNum)
            Calendar.JULY -> Triple("Jul", year, weekNum)
            Calendar.AUGUST -> Triple("Aug", year, weekNum)
            Calendar.SEPTEMBER -> Triple("Sep", year, weekNum)
            Calendar.OCTOBER -> Triple("Oct", year,  weekNum)
            Calendar.NOVEMBER -> Triple("Nov", year,  weekNum)
            Calendar.DECEMBER -> Triple("Dec", year, weekNum)
            else -> Triple("", 0, weekNum)
        }
    }

    // get the year from date
    fun getYearFromDateStr(dateStr: String) : Int {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        return cal.get(Calendar.YEAR)
    }

    // get the number of days in a month
    fun getMaximumNumberOfDaysInMonthFromDateStr(dateStr: String) : Int {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    // check if date is today
    fun isDateStrToday(dateStr: String) : Boolean {
        val now = getCurrentDateString()
        return now.equals(dateStr)
    }

    // check if date is weekend
    fun isDateStrWeekend(dateStr: String) : Boolean {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
    }

    // check if date is current month
    fun isDateStrCurrentMonth(dateStr: String) : Boolean {
        val dateObj = getDateObjFromDateString(dateStr)
        val cal = Calendar.getInstance()
        cal.time = dateObj
        val now = getCurrentDate()
        val nowCal = Calendar.getInstance()
        nowCal.time = now
        return (nowCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && nowCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH))
    }
}
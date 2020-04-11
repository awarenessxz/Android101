package com.learning.android101.fragments

import androidx.fragment.app.Fragment

abstract  class CalFragmentHolder : Fragment() {

    abstract fun goToToday()

    abstract fun showGoToDateDialog()

    abstract fun updateActionTitle(displayDate: String)

}
package com.learning.android101.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.learning.android101.R

/**
 * A simple [Fragment] subclass.
 */
class SwipeViewWithViewPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View =  inflater.inflate(R.layout.fragment_swipe_view_with_view_pager, container, false)
        val textview = rootView.findViewById<TextView>(R.id.swipe_view_msg)
        val msg = arguments?.getString("message")
        textview.text = msg
        return rootView
    }
}

package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.learning.android101.R
import com.learning.android101.fragments.DialogInFragFragment

class DialogInFragmentActivity : AppCompatActivity() {

    lateinit var fragmentContent : DialogInFragFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_in_fragment)

        fragmentContent = DialogInFragFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_fragmentHolder, fragmentContent)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
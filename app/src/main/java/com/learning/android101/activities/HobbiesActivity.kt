package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.android101.adapters.HobbiesAdapter
import com.learning.android101.R
import com.learning.android101.models.Supplier
import kotlinx.android.synthetic.main.activity_hobbies.*

class HobbiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hobbies)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val adapter = HobbiesAdapter(
            this,
            Supplier.hobbies
        )
        recyclerView.adapter = adapter      // recyclerView refers to the one inside activity_hobbies.xml
    }
}

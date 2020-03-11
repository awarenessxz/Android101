package com.learning.android101.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.learning.android101.models.Hobby
import com.learning.android101.R
import kotlinx.android.synthetic.main.rv_list_item.view.*

class HobbiesAdapter(val context: Context, private val hobbies: List<Hobby>) : RecyclerView.Adapter<HobbiesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // this basically refers to each individual cardview inside the recyclerview
        var currentHobby: Hobby? = null
        var currentPosition: Int = 0

        init {
            itemView.setOnClickListener {
                Toast.makeText(context, currentHobby!!.title + " Clicked!", Toast.LENGTH_SHORT).show()
            }

            itemView.imgShare.setOnClickListener {
                val message: String = "My hobby is " + currentHobby!!.title
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "text/plain"
                context.startActivity(Intent.createChooser(intent,  "Please select App"))
            }
        }


        fun setData(hobby: Hobby, pos: Int) {
            itemView.txvTitle.text = hobby.title
            this.currentHobby = hobby
            this.currentPosition = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hobby = hobbies[position]
        holder.setData(hobby, position)
    }
}

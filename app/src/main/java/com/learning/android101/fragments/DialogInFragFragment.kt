package com.learning.android101.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

import com.learning.android101.R
import com.learning.android101.activities.DialogInFragmentActivity
import kotlinx.android.synthetic.main.fragment_dialog_in_frag.*

/**
 * A simple [Fragment] subclass.
 */
class DialogInFragFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_in_frag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initOnClickListener()
        super.onActivityCreated(savedInstanceState)
    }

    private fun initOnClickListener() {

        btn_openDialog.setOnClickListener {
            // Initialize Dialog Components
            val activity = activity as DialogInFragmentActivity
            val dialog = AlertDialog.Builder(activity)
            val view = layoutInflater.inflate(R.layout.dialog_inside_fragment, null)
            val et_inputField = view.findViewById<EditText>(R.id.et_inputSomething)

            // Set up Dialog
            dialog.setView(view)
            dialog.setTitle("SOMETHING")
            dialog.setPositiveButton("ADD", null)
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }

            // Build AlertDialog
            val alertDialog = dialog.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (et_inputField.text.isEmpty()) {
                    et_inputField.error = "Please fill in something!"
                } else {
                    Toast.makeText(activity, "${et_inputField.text.toString()} is inputted", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
            }
        }
    }
}

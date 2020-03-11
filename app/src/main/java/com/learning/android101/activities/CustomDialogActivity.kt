package com.learning.android101.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_custom_dialog.*
import kotlinx.android.synthetic.main.dialog_custom.view.*

class CustomDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dialog)

        btn_simpleAlertDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Default Alert Dialog")
            dialog.setMessage("Are you sure  you wanna continue?")
            dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->
                Toast.makeText(this, "Positive Button clicked!", Toast.LENGTH_SHORT).show()
            }
            dialog.setNegativeButton("Cancel") { _:DialogInterface, _ : Int ->
                Toast.makeText(this, "Negative Button clicked!", Toast.LENGTH_SHORT).show()

            }
            dialog.show()
        }

        btn_customLayoutDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_custom, null)
            dialog.setView(view)
            dialog.show()

            view.img_btn1.setOnClickListener{
                val dialog2 = AlertDialog.Builder(this)
                val view2 = layoutInflater.inflate(R.layout.dialog_calender, null)
                dialog2.setTitle("Calender Dialog")
                dialog2.setView(view2)
                dialog2.setPositiveButton("Done") { _: DialogInterface, _: Int ->
                    Toast.makeText(this, "Dialog 2 OKAY!", Toast.LENGTH_SHORT).show()
                }
                dialog2.setNegativeButton("Cancel") { _:DialogInterface, _ : Int ->
                    Toast.makeText(this, "Dialog 2 Cancelled!", Toast.LENGTH_SHORT).show()

                }
                dialog2.show()
            }

            view.img_btn2.setOnClickListener {
                val dialog3 = AlertDialog.Builder(this)
                val sequence = arrayOf("A", "B", "C")
                dialog3.setTitle("Select: ")
                dialog3.setSingleChoiceItems(sequence, 0) {dialog, which ->
                    Toast.makeText(this, "selected ${sequence[which]}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                dialog3.setPositiveButton("Done") { _: DialogInterface, _: Int ->
                    Toast.makeText(this, "Dialog 3 OKAY!", Toast.LENGTH_SHORT).show()
                }
                dialog3.setNegativeButton("Cancel") { _:DialogInterface, _ : Int ->
                    Toast.makeText(this, "Dialog 3 Cancelled!", Toast.LENGTH_SHORT).show()

                }
                dialog3.show()
            }
        }

        btn_DialogWithValidation.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_edittext, null)

            val editTextField = view.findViewById<EditText>(R.id.et_validateDialog)

            dialog.setView(view)
            dialog.setTitle("Validate EditText")
            dialog.setPositiveButton("Validate", null)
            val alertDialog = dialog.create()
            alertDialog.show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{
                if (editTextField.text.isEmpty()) {
                    editTextField.setError("Field cannot be empty")
                } else {
                    Toast.makeText(this, "${editTextField.text} Not Empty", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
            }
        }
    }
}

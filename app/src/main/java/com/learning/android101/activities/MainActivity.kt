package com.learning.android101.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.learning.android101.Constants
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ******************** Set codes for on click listener ********************

        btnShowToast.setOnClickListener {
            Log.i("MainActivity", "Button was clicked!") // tag is so that you can filter easily inside logcat terminal
            Toast.makeText(this, "Button was clicked!", Toast.LENGTH_SHORT).show()
        }

        // Explicit Intent: Sending data to another activity
        btnSendMsgToNextActivity.setOnClickListener {
            val message: String = etUserMessage.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(Constants.USER_MSG_KEY, message)
            startActivity(intent)
        }

        // Implicit Intent: Sending data to other applications
        btnShareDataToOtherApps.setOnClickListener {
            val message: String = etUserMessage.text.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent,  "Share To"))
        }
    }

    // Menu Bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menubar, menu)
        return true
    }

    // Menu Bar OnClickListener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_tutorial_fab -> {
                // Tutorial for using Floating Action Button
                val intent = Intent(this, FABActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_labelled_fab -> {
                // Tutorial for using Floating Action Button with labels
                val intent = Intent(this, LabelledFabWithOverlayActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_rv -> {
                // Tutorial for using Recycler View
                val intent = Intent(this, HobbiesActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_et -> {
                // Tutorial for using EditText OnTextChangedListener
                val intent = Intent(this, EditTextActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_parcelable -> {
                // Tutorial for passing Parcelable Object into Intent to another activity
                val intent = Intent(this, PassParcelableObjectIntent1Activity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_botNavBar -> {
                // Tutorial for bottom navigation bar with fragment
                val intent = Intent(this, BotNavBarActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_navDrawer -> {
                // Tutorial for Navigation Drawer
                val intent = Intent(this, NavDrawerActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_navDrawerFrag -> {
                // Tutorial for Navigation Drawer with fragmentation
                val intent = Intent(this, NavDrawerWithFragActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_navDrawerWithBotNavBar -> {
                // Tutorial for Navigation Drawer + Bot Nav Bar
                val intent = Intent(this, NavDrawerWithBotNavBarActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_addMenuItemProgrammatically -> {
                // Tutorial for adding menu item programatically
                val intent = Intent(this, AddMenuItemProgrammaticallyActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_customDialog -> {
                // Tutorial for custom dialog
                val intent = Intent(this, CustomDialogActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_textSpinner -> {
                // Tutorial for Text Spinner
                val intent = Intent(this, TextSpinnerActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_sqlite -> {
                // Tutorial for SQLite
                val intent = Intent(this, SQLiteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_fragmentWithDialog -> {
                // Tutorial for opening dialog inside fragment
                val intent = Intent(this, DialogInFragmentActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_singleton_DB -> {
                // Tutorial for having a Singleton DB design
                val intent = Intent(this, SingletonDB1Activity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_datetimepicker -> {
                // Tutorial for datetime picker
                val intent = Intent(this, DateTimePickerActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_startActivityForResult -> {
                // Tutorial for datetime picker
                val intent = Intent(this, StartActivityForResultActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_tutorial_addLayerDynamically -> {
                // Tutorial for adding layer/view dynamically
                val intent = Intent(this, AddLayerDynamicallyActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

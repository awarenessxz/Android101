package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_add_menu_item_programmatically.*

class AddMenuItemProgrammaticallyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu_item_programmatically)
        addMenuItem()
    }

    // This works for Adding Menu Item to Navigation Drawer
    private fun addMenuItem() {
        val subMenu = nv_addMenuItem.menu.findItem(R.id.menu_group_program).subMenu
        subMenu.add(0, Menu.FIRST, 0, "Foo").setIcon(R.drawable.plus)
        subMenu.add(1, Menu.FIRST, 1, "Bar").setIcon(R.drawable.plus)

        val menu = nv_addMenuItem.menu
        menu.add("New Item")
    }
}

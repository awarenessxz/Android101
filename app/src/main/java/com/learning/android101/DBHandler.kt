package com.learning.android101

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHandler (var context: Context) : SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE ${Constants.TABLE_VALUES} ( " +
                "${Constants.COL_ID} integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.COL_TITLE} varchar(100) NOT NULL " +
                ");"
        db?.execSQL(createTableQuery)

        // Add default Value
        addDefaultValue(db, "Default")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun addDefaultValue(db: SQLiteDatabase?, value: String) {
        val cv = ContentValues()
        cv.put(Constants.COL_TITLE, value)
        db?.insert(Constants.TABLE_VALUES, null, cv)
    }

    fun addNewValue(value: String) : Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Constants.COL_TITLE, value)
        var result = db.insert(Constants.TABLE_VALUES, null, cv)
        db.close()
        return result != (-1).toLong()
    }

    fun getResults() : MutableList<String> {
        var resultList: MutableList<String> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM ${Constants.TABLE_VALUES}"
        var result = db?.rawQuery(query, null)

        result?.let {
            if (result.moveToFirst()) {
                do {
                    val title = result.getString(result.getColumnIndex(Constants.COL_TITLE))
                    resultList.add(title)
                } while (result.moveToNext())
            }
        }

        db.close()
        return resultList
    }

    fun deleteValues(value: String) {
        val db = this.writableDatabase
        db.delete(Constants.TABLE_VALUES, "${Constants.COL_TITLE}=?", arrayOf(value))
    }

}
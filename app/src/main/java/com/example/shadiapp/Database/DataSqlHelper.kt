package com.example.shadiapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shadiapp.util.TableDetails
import com.example.shadiapp.util.Utility

class DataSqlHelper(var context: Context) : SQLiteOpenHelper(context, "Detail.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?)  {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS " + TableDetails.Match_List_Table + " ("
                + TableDetails.ID + " INTEGER PRIMARY KEY , " + TableDetails.Match_LIST_ID + " INTEGER ," + TableDetails.JSON + " TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(db)
    }

    fun insertRows(db: SQLiteDatabase, table_name: String, contentValues: ContentValues?): Long {
        val n = db.insert(table_name, null, contentValues)
        return n
    }

    fun updateRows(db: SQLiteDatabase, table_name: String?, values: ContentValues, whereClause: String?, whereArgs: String): Long {
        val n = db.update(table_name, values, whereClause, arrayOf(whereArgs)).toLong()
        return n
    }

    fun getValueByTableName(db: SQLiteDatabase, table_name: String): Cursor {
        val cursor = db.query(table_name, null, null, null, null, null, null)
        Utility.printMessage("Count  " + table_name + "  " + cursor.count)
        return cursor
    }

    fun CheckIsDataAlreadyInDBorNotStringId(
        context: Context,
        TableName: String,
        colume: String,
        id: String, db: SQLiteDatabase
    ): Boolean {
        var check = false

        val Query = "Select * from $TableName where $colume = '$id'"
        val cursor = db.rawQuery(Query, null)
        check = if (cursor.count <= 0) {
            cursor.close()
            false
        } else {
            true
        }
        cursor.close()
        return check
    }

}
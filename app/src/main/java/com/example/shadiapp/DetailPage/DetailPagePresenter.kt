package com.example.shadiapp.DetailPage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.shadiapp.Database.DataSqlHelper
import com.example.shadiapp.dataclass.ResponseData
import com.example.shadiapp.util.ParseJson
import com.example.shadiapp.util.TableDetails

class DetailPagePresenter(val context: Context, val detailPageInterface: DetailPageInterface) {

    fun saveDataToDb(responseData: ResponseData) {
        val json = ParseJson.getJsonOfData(responseData)
        val dataSQLHelper = DataSqlHelper(context)
        val sqLiteDatabase: SQLiteDatabase
        sqLiteDatabase = dataSQLHelper.writableDatabase
        val contentValues = ContentValues()
        val id = responseData.id
        contentValues.put(TableDetails.Match_LIST_ID, id)
        contentValues.put(TableDetails.JSON, json.toString())
        if (dataSQLHelper.CheckIsDataAlreadyInDBorNotStringId(
                context,
                TableDetails.Match_List_Table,
                TableDetails.Match_LIST_ID,
                id.toString(),
                sqLiteDatabase
            )
        ) {
            dataSQLHelper.updateRows(
                sqLiteDatabase,
                TableDetails.Match_List_Table,
                contentValues,
                TableDetails.Match_LIST_ID + "= ?",
                id.toString()
            )
        } else {
            dataSQLHelper.insertRows(sqLiteDatabase, TableDetails.Match_List_Table, contentValues)
        }
        sqLiteDatabase.close()
        dataSQLHelper.close()
        detailPageInterface.setSelectedDone()
    }
}
package com.example.shadiapp.MatchList

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.shadiapp.Database.DataSqlHelper
import com.example.shadiapp.Network.ApiClient
import com.example.shadiapp.Network.ApiInterface
import com.example.shadiapp.Network.OnResponseListener
import com.example.shadiapp.dataclass.ResponseData
import com.example.shadiapp.util.ParseJson
import com.example.shadiapp.util.TableDetails
import com.example.shadiapp.util.Utility
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MatchListPresenter(val context: Context,val onResponseListner: OnResponseListener , val matchListInterface: MatchListInterface) {
    fun parseJson(response:ResponseData?){
        if(response!=null){
            onResponseListner.onSuccess("Success",response)
        }else{
            onResponseListner.onError("Server Down")
        }
    }

    fun hitApi(){
        val api = ApiClient.getClient()!!.create(ApiInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val observable = api.getMatchList()
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val response = Gson().fromJson(result, ResponseData::class.java)
                        response.id = 0
                        matchListInterface.getResponse(response)
                    },
                    { error ->
                        matchListInterface.getResponse( null)
                    }
                )

        }
    }


    fun saveToDb(response: ResponseData?){
        if(response!=null){
            val dataSQLHelper = DataSqlHelper(context)
            val sqLiteDatabase: SQLiteDatabase
            sqLiteDatabase = dataSQLHelper.writableDatabase
            val contentValues = ContentValues()
            val id = response.id
            contentValues.put(TableDetails.Match_LIST_ID, id)
            contentValues.put(TableDetails.JSON, ParseJson.getJsonOfData(response).toString())
            if (dataSQLHelper.CheckIsDataAlreadyInDBorNotStringId(context, TableDetails.Match_List_Table, TableDetails.Match_LIST_ID, id.toString(), sqLiteDatabase)) {
                dataSQLHelper.updateRows(sqLiteDatabase, TableDetails.Match_List_Table, contentValues, TableDetails.Match_LIST_ID + "= ?", id.toString())
            }else {
                dataSQLHelper.insertRows(sqLiteDatabase, TableDetails.Match_List_Table, contentValues)
            }
            sqLiteDatabase.close()
            dataSQLHelper.close()
            onResponseListner.onSuccess("Success",response)
        }else{
            onResponseListner.onError("Server Down")
        }
    }

    fun getListFromDb(){
        val dataSQLHelper = DataSqlHelper(context)
        val sqLiteDatabase: SQLiteDatabase
        sqLiteDatabase = dataSQLHelper.writableDatabase
        val cursor: Cursor = dataSQLHelper.getValueByTableName(sqLiteDatabase, TableDetails.Match_List_Table)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                Utility.printMessage("Response JSON" + cursor.getString(cursor.getColumnIndex(TableDetails.JSON)))
                val json = Gson().fromJson(cursor.getString(cursor.getColumnIndex(TableDetails.JSON)), ResponseData::class.java)
                json.id = cursor.getInt(cursor.getColumnIndex(TableDetails.Match_LIST_ID))
                matchListInterface.getResponse(json)
                cursor.moveToNext()
            }
        }
        cursor.close()
        sqLiteDatabase.close()
        dataSQLHelper.close()
    }
}
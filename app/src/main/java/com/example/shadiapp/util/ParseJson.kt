package com.example.shadiapp.util

import com.example.shadiapp.dataclass.ResponseData
import com.google.gson.Gson
import org.json.JSONObject

class ParseJson {
    companion object{
        fun getJsonOfData(responseData: ResponseData) :JSONObject{
            val data = Gson().toJson(responseData)
            Utility.printMessage(data)
            val json = JSONObject(data)
            return json
        }
    }
}
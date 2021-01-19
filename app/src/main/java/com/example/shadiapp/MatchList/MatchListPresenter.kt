package com.example.shadiapp.MatchList

import android.content.Context
import com.example.shadiapp.Network.OnResponseListener
import com.example.shadiapp.dataclass.ResponseData

class MatchListPresenter(val context: Context,val onResponseListner: OnResponseListener) {
    fun parseJson(response:ResponseData?){
        if(response!=null){
            onResponseListner.onSuccess("Success",response)
        }else{
            onResponseListner.onError("Server Down")
        }
    }
}
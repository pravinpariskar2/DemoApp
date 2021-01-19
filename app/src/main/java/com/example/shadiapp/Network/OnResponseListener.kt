package com.example.shadiapp.Network

import com.example.shadiapp.dataclass.ResponseData

interface OnResponseListener {
    fun onSuccess(message: String, response: ResponseData)

    fun onError(message: String)

}
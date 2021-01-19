package com.example.shadiapp.Network

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {
    @GET("/api/?results=10")
    fun getMatchList(): Observable<JsonElement>
}
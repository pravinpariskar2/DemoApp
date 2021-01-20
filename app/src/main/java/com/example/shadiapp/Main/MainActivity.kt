package com.example.shadiapp.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shadiapp.R
import com.example.shadiapp.dataclass.ResponseData
import com.example.shadiapp.dataclass.Result

class MainActivity : AppCompatActivity() {
    var responseData: ResponseData?= null
    var selectedPosition =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
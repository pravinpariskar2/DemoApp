package com.example.shadiapp.CustomDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.shadiapp.R

class CustomProgressDialog(context: Context) : Dialog(context, R.style.Theme_ShadiApp) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progressdialog_custom)
    }
}
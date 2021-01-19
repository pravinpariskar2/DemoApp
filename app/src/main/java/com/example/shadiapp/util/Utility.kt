package com.example.shadiapp.util

import android.content.Context
import android.net.ConnectivityManager

class Utility {
    companion object{
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            return if (ni == null) {
                // There are no active networks.
                false
            } else true
        }

        fun printMessage(message: String) {
            System.out.println("@@@ MOVIES APP @@@ :" + message)
        }
    }

}
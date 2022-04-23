package com.neugelb.utils

import android.app.Activity
import android.view.LayoutInflater
import com.irozon.sneaker.Sneaker

class SnackBar {
    companion object {

        fun showError(activity: Activity,text:String) {
            Sneaker.with(activity)
                .setMessage(text)
                .sneakError()
        }

        fun showSuccess(activity: Activity,text:String) {
            Sneaker.with(activity)
                .setMessage(text)
                .sneakSuccess()
        }

        fun showCustom(activity: Activity,text:String, idRes: Int) {
            val sneaker = Sneaker.with(activity) // Activity, Fragment or ViewGroup
            val view = LayoutInflater.from(activity).inflate(idRes,  sneaker.getView(), false)
            // Your custom view code
//            view.findViewById<TextView>(R.id.tvInstall).setOnClickListener{
//                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
//            }
            sneaker.sneakCustom(view)
        }
    }
}
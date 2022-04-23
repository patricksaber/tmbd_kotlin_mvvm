package com.neugelb.utils

import android.app.AlertDialog
import android.content.Context
import com.neugelb.R
import com.neugelb.interfaces.Connect
import com.neugelb.utils.ConnectionDetector.haveInternet

object Dialogs {
    fun openConnectionDialog(context: Context, callBack: Connect) {
        if (!haveInternet()) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(context.getString(R.string.connection_error))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.data_offline)) { dialog, _ ->

                    dialog.dismiss()
                    callBack.getDataOffline()
                }
                .setNegativeButton(context.getString(R.string.retry)) { dialog, _ ->
                    if (haveInternet()) {
                        dialog.dismiss()
                        callBack.retry()
                    } else openConnectionDialog(context, callBack)
                }
            val alert = builder.create()
            alert.show()
        }else callBack.isConnected()
    }

    fun retryConnectionDialog(context: Context, callBack: Connect) {
        if (!haveInternet()) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(context.getString(R.string.connection_error))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton(context.getString(R.string.retry)) { dialog, _ ->
                    if (haveInternet()) {
                        dialog.dismiss()
                        callBack.retry()
                    } else retryConnectionDialog(context,callBack)
                }
            val alert = builder.create()
            alert.show()
        }else callBack.isConnected()
    }
}
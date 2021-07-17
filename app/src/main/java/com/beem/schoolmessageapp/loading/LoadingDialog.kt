package com.example.qms.loading

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color

import android.util.Log
import com.beem.schoolmessageapp.R
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog


class LoadingDialog( ) {

    lateinit var alertDialogs: LottieAlertDialog

    fun load(messages: String, context: Context): LottieAlertDialog {

//         alertDialog = LottieAlertDialog
        alertDialogs = LottieAlertDialog.Builder(context, DialogTypes.TYPE_LOADING)
            .setTitle("Loading")
            .setDescription(messages)
            .build()
        alertDialogs.setCancelable(false)

        return alertDialogs

    }

    fun closeDialog() {
        if(alertDialogs == null){

        }
        alertDialogs.dismiss()
    }

    fun errors(messages: String, context: Context,tile:String) {

        var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_ERROR)
            .setTitle(tile)
            .setDescription(messages)
            .setPositiveText("OK")
            .setPositiveTextColor(context.getResources().getColor(R.color.white))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                }
            }).build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    fun success(messages: String, context: Context,title:String) {

        var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_SUCCESS)
            .setTitle(title)
            .setDescription(messages)
            .setPositiveText("OK")
            .setPositiveTextColor(context.getResources().getColor(R.color.white))

            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()



                }
            }
            )
            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun warning(messages: String, context: Activity,title:String) {

        var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_WARNING)
            .setTitle(title)
            .setDescription(messages)
            .setPositiveText("OK")
            .setPositiveTextColor(context.getResources().getColor(R.color.white))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {

                    dialog.dismiss()

                }
            }).build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }



}
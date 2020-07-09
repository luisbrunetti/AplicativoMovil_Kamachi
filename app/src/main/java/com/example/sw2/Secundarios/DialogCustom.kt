package com.example.sw2.Secundarios

import android.app.Dialog
import android.content.Context

class DialogCustom : Dialog {
    constructor(context: Context) : super(context) {
        this.setCancelable(false)
    }
    constructor(context: Context, themresID:Int):super(context,themresID){
        this.setCancelable(false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.dismiss()
    }
}
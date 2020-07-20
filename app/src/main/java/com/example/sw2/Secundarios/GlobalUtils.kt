package com.example.sw2.Secundarios

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.RatingBar
import android.widget.TextView
import com.example.sw2.R
import java.util.zip.Inflater

object GlobalUtils {
    fun showdialog(context: Context,DialogCallback:DialogCallback){
        val dialog = DialogCustom(context, R.style.CustomDialogTheme)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.layout_dialog,null)
        dialog.setContentView(v)

        val btnCalificar = dialog.findViewById<TextView>(R.id.tvi_dialog_calificar)
        val rating = dialog.findViewById<RatingBar>(R.id.ratingBar_calificar)
        btnCalificar.setOnClickListener {
            Log.d("cal",rating!!.numStars.toString())
            DialogCallback.callback(rating?.numStars)
            dialog.dismiss()
        }
        dialog.show()
    }
}
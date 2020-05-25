package com.example.sw2.patrones_diseño.factory

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sw2.R

class MyviewHolder(itemView: View, OnClickList: IntefaceClickListeer) : RecyclerView.ViewHolder(itemView) ,
    View.OnClickListener {
    var OnClickListenerHolder: IntefaceClickListeer
    var tv_nombreTrabajo: TextView? = null
    var tv_distrito: TextView? = null
    var tv_ImagenView: ImageView

    init {
        tv_nombreTrabajo = itemView.findViewById(R.id.textTrabajo)
        tv_distrito = itemView.findViewById(R.id.textDistrito)
        tv_ImagenView = itemView.findViewById(R.id.imageViewHolder)
        this.OnClickListenerHolder = OnClickList
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        OnClickListenerHolder.onClickListener(adapterPosition)
    }
}
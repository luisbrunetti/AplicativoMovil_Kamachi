package com.example.sw2.framents.secundarios

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sw2.R
import com.example.sw2.patrones_diseño.RecycleViewHome.IntefaceClickListeer

class MyViewHolderAfiliacionFragment(itemView: View, OnClickList: IntefaceClickListeer) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

    var OnClickListenerHolder: IntefaceClickListeer
    var tv_nombreTrabajo: TextView? = null
    //var tv_distrito: TextView? = null
    //var tv_ImagenView: ImageView

    init {
        tv_nombreTrabajo = itemView.findViewById(R.id.title_template_service_afiliacion)
        //tv_distrito = itemView.findViewById(R.id.textDistrito)
        //tv_ImagenView = itemView.findViewById(R.id.imageViewHolder)
        this.OnClickListenerHolder = OnClickList
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        OnClickListenerHolder.onClickListener(adapterPosition)
    }
}
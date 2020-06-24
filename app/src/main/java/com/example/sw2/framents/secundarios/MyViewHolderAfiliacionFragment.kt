package com.example.sw2.framents.secundarios

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sw2.R
import com.example.sw2.interfaces.IntefaceClickListeer
import org.w3c.dom.Text

class MyViewHolderAfiliacionFragment(itemView: View, OnClickList: IntefaceClickListeer) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

    var OnClickListenerHolder: IntefaceClickListeer
    var tv_nombreTrabajo: TextView? = null
    var tv_costservice :TextView? = null
    var tv_categoriaservicio:TextView? = null
    var iv_imagenServicio:ImageView? = null
    init {
        tv_nombreTrabajo = itemView.findViewById(R.id.title_template_service_afiliacion)
        tv_costservice = itemView.findViewById(R.id.costo_template_service_afiliacion_value)
        tv_categoriaservicio = itemView.findViewById(R.id.categoria_template_service_afiliacion_value)
        iv_imagenServicio = itemView.findViewById(R.id.iv_template_newservice)
        this.OnClickListenerHolder = OnClickList
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        OnClickListenerHolder.onClickListener(adapterPosition)
    }
}
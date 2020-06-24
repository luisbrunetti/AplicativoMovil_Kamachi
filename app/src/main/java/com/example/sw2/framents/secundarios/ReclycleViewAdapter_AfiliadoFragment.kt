package com.example.sw2.framents.secundarios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.Servicio_profile_afiliacion
import com.example.sw2.R
import com.example.sw2.interfaces.IntefaceClickListeer


class ReclycleViewAdapter_AfiliadoFragment(var mcontext: Context, mdata: ArrayList<Servicio_profile_afiliacion>, clickLister: IntefaceClickListeer): RecyclerView.Adapter<MyViewHolderAfiliacionFragment>(){
    private var mdata : ArrayList<Servicio_profile_afiliacion> = mdata
    private var onClickListener : IntefaceClickListeer = clickLister
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderAfiliacionFragment {
        val v : View = LayoutInflater.from(mcontext).inflate(R.layout.template_listservices_afiliacionfragment,parent,false)
        val vHolder =
            MyViewHolderAfiliacionFragment(
                v,
                onClickListener
            )
        return vHolder
    }
    override fun getItemCount(): Int {
        return mdata.size
    }
    override fun onBindViewHolder(holder: MyViewHolderAfiliacionFragment, position: Int) {
        holder.tv_nombreTrabajo!!.text = mdata[position].nombreTrabajo
        holder.tv_categoriaservicio!!.text = mdata[position].categoria_servicio
        holder.tv_costservice!!.text = mdata[position].cost_service + " soles"
        Glide.with(mcontext)
            .load(mdata[position].Uri)
            .fitCenter()
            //.apply(RequestOptions.overrideOf(160,180))
            .centerCrop()
            .into(holder.iv_imagenServicio!!)
    }
}
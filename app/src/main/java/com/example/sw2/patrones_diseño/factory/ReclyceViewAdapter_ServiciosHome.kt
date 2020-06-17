package com.example.sw2.patrones_diseño.factory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R

class ReclyceViewAdapter_ServiciosHome(var mcontext:Context, mdata: ArrayList<ServicioListView>, clickLister: IntefaceClickListeer): RecyclerView.Adapter<MyviewHolder>() {
    private var mdata : ArrayList<ServicioListView> = mdata
    private var onClickListener : IntefaceClickListeer = clickLister
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val v : View  = LayoutInflater.from(mcontext).inflate(R.layout.template_listview_homefragment,parent,false)
        val vHolder =
            MyviewHolder(
                v,
                onClickListener
            )
        return vHolder
    }
    override fun getItemCount(): Int {
        return mdata.size
    }
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.tv_nombreTrabajo?.text = mdata[position].NombreTrabaj
        holder.tv_distrito?.text= mdata[position].Distrito
        //holder.tv_distrito?.text = mdata[position].Imagen
        //holder.tv_ImagenView.setBackgroundResource(R.drawable.circular_imageview)
        Glide.with(mcontext)
            .load(mdata[position].UriImagen)
            .fitCenter()
            .apply(RequestOptions.overrideOf(160,180))
            .centerCrop()
            .into(holder.tv_ImagenView)
    }
}
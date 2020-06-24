package com.example.sw2.framents.secundarios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.example.sw2.patrones_diseño.RecycleViewHome.IntefaceClickListeer
import com.example.sw2.patrones_diseño.RecycleViewHome.MyviewHolder

class ReclycleViewAdapter_AfiliadoFragment(var mcontext: Context, mdata: ArrayList<ServicioListView>, clickLister: IntefaceClickListeer): RecyclerView.Adapter<MyViewHolderAfiliacionFragment>(){
    private var mdata : ArrayList<ServicioListView> = mdata
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
        holder.tv_nombreTrabajo!!.text = mdata[position].NombreTrabaj
        //holder.tv_distrito?.text= mdata[position].Distrito
        //holder.tv_distrito?.text = mdata[position].Imagen
        //holder.tv_ImagenView.setBackgroundResource(R.drawable.circular_imageview)
        /*Glide.with(mcontext)
            .load(mdata[position].UriImagen)
            .fitCenter()
            .apply(RequestOptions.overrideOf(160,180))
            .centerCrop()
            .into(holder.tv_ImagenView)*/
    }
}
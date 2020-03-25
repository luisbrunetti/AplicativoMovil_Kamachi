package com.example.sw2.Clases

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.R

class ReclyceViewAdapter(var mcontext:Context, mdata: ArrayList<ServicioListView> , it: Uri): RecyclerView.Adapter<ReclyceViewAdapter.MyviewHolder>() {
    private var mdata : ArrayList<ServicioListView> = mdata
    private var it : Uri = it
    class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_nombreTrabajo: TextView? = null
        var tv_distrito:TextView? = null
        var tv_ImagenView :ImageView

        init{
            tv_nombreTrabajo = itemView.findViewById(R.id.textTrabajo)
            tv_distrito = itemView.findViewById(R.id.textDistrito)
            tv_ImagenView = itemView.findViewById(R.id.imageViewHolder)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val v : View  = LayoutInflater.from(mcontext).inflate(R.layout.template_listview_homefragment,parent,false)
        val vHolder = MyviewHolder(v)
        return vHolder
    }
    override fun getItemCount(): Int {
        return mdata.size
    }
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.tv_nombreTrabajo?.text = mdata[position].NombreTrabaj
        holder.tv_distrito?.text= mdata[position].Distrito
        //holder.tv_distrito?.text = mdata[position].Imagen
        Glide.with(mcontext)
            .load(it)
            .fitCenter()
            .apply(RequestOptions.overrideOf(200,180))
            .centerCrop()
            .into(holder.tv_ImagenView)

    }


}
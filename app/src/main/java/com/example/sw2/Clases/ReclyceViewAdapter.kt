package com.example.sw2.Clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sw2.R

class ReclyceViewAdapter(var mcontext:Context, mdata: ArrayList<ServicioListView>): RecyclerView.Adapter<ReclyceViewAdapter.MyviewHolder>() {
    private var mdata : ArrayList<ServicioListView>
    init{

        this.mdata = mdata
    }
    class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_nombreTrabajo: TextView? = null
        var tv_distrito:TextView? = null
        init{
            tv_nombreTrabajo = itemView.findViewById(R.id.textTrabajo)
            tv_distrito = itemView.findViewById(R.id.textDistrito)
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
        holder.tv_nombreTrabajo?.text = mdata[position].NombreTrabajo
        holder.tv_distrito?.text= mdata[position].Distrito
    }
}
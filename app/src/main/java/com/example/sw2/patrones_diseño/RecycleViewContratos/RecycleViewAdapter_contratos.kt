package com.example.sw2.patrones_diseño.RecycleViewContratos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.Contratos
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.example.sw2.interfaces.IntefaceClickListeer
import com.example.sw2.patrones_diseño.RecycleViewHome.MyviewHolder

class RecycleViewAdapter_contratos(var mcontext: Context, mdata: ArrayList<Contratos>, clickLister: IntefaceClickListeer): RecyclerView.Adapter<RecycleViewAdapter_contratos.MyviewHolderContratos>() {
    private var mdata : ArrayList<Contratos> = mdata
    private var onClickListener : IntefaceClickListeer = clickLister
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolderContratos {
        val v : View = LayoutInflater.from(mcontext).inflate(R.layout.template_listcontratos_profilefragment,parent,false)
        val vHolder =
            MyviewHolderContratos(
                v,
                onClickListener
            )
        return vHolder
    }
    override fun getItemCount(): Int {
        return mdata.size
    }
    override fun onBindViewHolder(holder:MyviewHolderContratos, position: Int) {
        holder.tv_nombreTrabajo?.text = mdata[position].Nombre_servicio_contratado
        holder.tv_empresa?.text = mdata[position].empresa
        holder.tv_estado?.text = mdata[position].Estado
        holder.tv_fecha?.text = mdata[position].Date
        holder.tv_costo?.text = "S/. "+mdata[position].Mount 
        Glide.with(mcontext)
            .load(mdata[position].Uri_Servicio_Contratado)
            .fitCenter()
            .apply(RequestOptions.overrideOf(160,180))
            .centerCrop()
            .into(holder.iv_contratado!!)
    }
    class MyviewHolderContratos(itemView: View, OnClickList: IntefaceClickListeer) : RecyclerView.ViewHolder(itemView) ,
        View.OnClickListener {
        var OnClickListenerHolder: IntefaceClickListeer
        var tv_nombreTrabajo: TextView? = null
        var tv_empresa:TextView? = null
        var tv_fecha:TextView? = null
        var tv_costo:TextView? = null
        var tv_estado:TextView? = null
        var iv_contratado:ImageView? = null
        init {
            tv_nombreTrabajo = itemView.findViewById(R.id.title_template_contrato)
            tv_empresa = itemView.findViewById(R.id.NombreEmpresa_template_contrato_value)
            tv_fecha = itemView.findViewById(R.id.fecha_template_contrato_value)
            tv_costo = itemView.findViewById(R.id.costo_template_contrato_value)
            tv_estado = itemView.findViewById(R.id.estado_template_contrato_value)
            iv_contratado = itemView.findViewById(R.id.iv_template_contrato)
            this.OnClickListenerHolder = OnClickList
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            OnClickListenerHolder.onClickListener(adapterPosition)
        }
    }
}

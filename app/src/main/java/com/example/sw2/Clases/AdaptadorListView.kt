package com.example.sw2.Clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.sw2.R


class AdaptadorListView(var contexto : FragmentActivity?, items:ArrayList<ServicioListView>):BaseAdapter() {
    var item: ArrayList<ServicioListView> = ArrayList(items)



    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var viewHolde : ViewHolder? = null
        var vista: View? =  p1

        Toast.makeText(contexto,vista.toString(),Toast.LENGTH_LONG).show()
        if(vista ==null){
            vista =LayoutInflater.from(contexto).inflate(R.layout.template_listview_homefragment,null)
            viewHolde =ViewHolder(vista)
            vista.tag =viewHolde

        }else{
            viewHolde= vista.tag as ViewHolder
        }

        val item = getItem(p0) as ServicioListView
        viewHolde.nombre?.text = item.NombreTrabaj
        viewHolde.distrito?.text = item.Distrito
        viewHolde.foto?.setImageResource(item.Imagen)

        return vista!!

    }

    override fun getItem(p0: Int): Any {
        return item[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return item.count()
    }
    private class ViewHolder(vista:View){ // PARA EL TEMPLEATE SE CREA UN OBJETO POR CADA CONTACTO
        var nombre: TextView? = null
        var foto: ImageView? = null
        var distrito: TextView? = null
        init{
            nombre = vista.findViewById(R.id.textTrabajo)
            distrito = vista.findViewById(R.id.textDistrito)
            foto = vista.findViewById(R.id.fotoServicio)
        }
    }
}
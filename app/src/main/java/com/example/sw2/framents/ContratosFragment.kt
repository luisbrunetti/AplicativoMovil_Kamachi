package com.example.sw2.framents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sw2.R
import com.example.sw2.interfaces.toolbar_transaction

class ContratosFragment: Fragment(){

    var int_toolbar_trans_dealsfrag:toolbar_transaction? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contratos,container,false)
        val button = view.findViewById<Button>(R.id.buttonaapretar)
        button.setOnClickListener {
        }
        setHasOptionsMenu(true)
        int_toolbar_trans_dealsfrag?.change_tittle("Contratos")
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        int_toolbar_trans_dealsfrag = activity as toolbar_transaction
    }


}
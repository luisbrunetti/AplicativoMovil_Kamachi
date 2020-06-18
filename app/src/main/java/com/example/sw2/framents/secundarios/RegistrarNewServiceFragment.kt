package com.example.sw2.framents.secundarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sw2.R

class RegistrarNewServiceFragment():Fragment(){
    private var vistaRegisterNewService: View ? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vistaRegisterNewService = inflater.inflate(R.layout.fragment_register_newservice,container,false)



        return vistaRegisterNewService
    }

}
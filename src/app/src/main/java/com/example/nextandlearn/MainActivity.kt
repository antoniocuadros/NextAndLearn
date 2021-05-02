package com.example.nextandlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Establecemos el controlador del menu inferior
        estableceControlNavegacion()


    }


    private fun estableceControlNavegacion(){
        val menu_inferior = findViewById<BottomNavigationView>(R.id.menu_inferior)
        val controlador_navegacion = findNavController(R.id.fragment2)
        menu_inferior.setupWithNavController(controlador_navegacion)
    }
}
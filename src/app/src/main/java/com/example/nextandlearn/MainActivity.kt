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

    /*
    Este método sirve para vincular nuestro Navigation Component con el menú inferior
    con el objetivo de que al pulsar sobre un item del menú se cargue el fragmento
    asociado a dicho item del menú.
     */
    private fun estableceControlNavegacion(){
        val menu_inferior = findViewById<BottomNavigationView>(R.id.menu_inferior)
        val controlador_navegacion = findNavController(R.id.fragment2)
        menu_inferior.setupWithNavController(controlador_navegacion)
    }
}
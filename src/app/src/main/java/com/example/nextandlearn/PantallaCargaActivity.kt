package com.example.nextandlearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlin.system.exitProcess

/*
    En esta clase simplemente mostramos un SplashScreen mientras la aplicación carga en el inicio
    con un logo de la aplicación.
 */
class PantallaCargaActivity : AppCompatActivity() {
    /*
        En este método simplemente creamos un intent a la actividad principal MainActivity
        con un delay de 1 segundo para que se muestre esta pestaña de carga y luego cargue
        la actividad principal.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)

        Handler(Looper.getMainLooper()).postDelayed({
            var intent_main_activity = Intent(this, MainActivity::class.java)
            startActivity(intent_main_activity)
            finish()
        }, 1000)

    }
}
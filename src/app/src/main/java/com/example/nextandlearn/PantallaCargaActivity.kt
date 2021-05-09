package com.example.nextandlearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class PantallaCargaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)

        Handler(Looper.getMainLooper()).postDelayed({
            var intent_main_activity = Intent(this, MainActivity::class.java)
            startActivity(intent_main_activity)
        }, 1000)

    }
}
package com.example.nextandlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nextandlearn.fragmentos.fragmentoListaVocabularioDirections
import com.example.nextandlearn.fragmentos.fragmentoMenuTestsVocabularioDirections
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var db:VocabularioDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Establecemos el controlador del menu inferior
        estableceControlNavegacion()

        //obtenemos el objeto db
        //obtener_db()
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

    /*
        Esta función se encarga de obtener la base de datos
     */
    private fun obtener_db(){
        obtenerBaseDatos(applicationContext)
    }

    //De la lista de colecciones al menu de tests o vocabulario
    fun onColeccionSelected(coleccion: String) {
        findNavController(R.id.fragment2).navigate(fragmentoListaVocabularioDirections.actionListaVocabularioToFragmentoMenuTestsVocabulario(coleccion))
    }

    //Del menu de tests o vocabulario a la lista de palabras
    fun onVocabularioSelected(coleccion: String){
        findNavController(R.id.fragment2).navigate(fragmentoMenuTestsVocabularioDirections.actionFragmentoMenuTestsVocabularioToFragmentoCartasPalabras(coleccion))
    }

    //Del menu de tests o vocabulario a los tests
    fun onTestsSelected(coleccion: String){
        findNavController(R.id.fragment2).navigate(fragmentoMenuTestsVocabularioDirections.actionFragmentoMenuTestsVocabularioToTests(coleccion))
    }
}
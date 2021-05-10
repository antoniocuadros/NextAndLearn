package com.example.nextandlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nextandlearn.fragmentos.*
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
    Esta clase representa la actividad principal de la aplicación. Esta clase releva toda la lógica
    en los distintos fragmentos que se han utilizado, de esta forma en esta clase únicamente encontraremos
    métodos para cambiar entre los distintos fragmentos que serán los que aporten funcionalidad a nuestra
    aplicación.
 */
class MainActivity : AppCompatActivity() {
    /*
        El método onCreate se llama al inicio de la actividad para vincular la vista correspondiente.
        Además se establece el menú inferior y su funcionamiento.
     */
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


    /*
        Esta función nos sirve para navegar de la lista de colecciones al menu de tests o vocabulario
     */
    fun onColeccionSelected(coleccion: String) {
        findNavController(R.id.fragment2).navigate(fragmentoListaVocabularioDirections.actionListaVocabularioToFragmentoMenuTestsVocabulario(coleccion))
    }

    /*
        Esta función nos sirve para navegar de la lista de colecciones al menu de tests o vocabulario.
     */
    fun onVocabularioSelected(coleccion: String){
        findNavController(R.id.fragment2).navigate(fragmentoMenuTestsVocabularioDirections.actionFragmentoMenuTestsVocabularioToFragmentoCartasPalabras(coleccion))
    }

    /*
        Esta función nos sirve para navegar del menu de tests o vocabulario a los tests
     */
    fun onTestsSelected(coleccion: String, opcion:Int){
        findNavController(R.id.fragment2).navigate(fragmentoMenuTestsVocabularioDirections.actionFragmentoMenuTestsVocabularioToTests(coleccion, opcion))
    }

    /*
        Esta función nos sirve para navegar del menu de tests o vocabulario a los tests
     */
    fun fromTestsToColecciones(){
        findNavController(R.id.fragment2).navigate(fragmentoTestsDirections.actionTestsToListaVocabulario())
    }

    /*
        Esta función nos sirve para navegar de la lista de niveles a colecciones
     */
    fun fromNivelesToColecciones(nombre:String){
        findNavController(R.id.fragment2).navigate(fragmentoNivelesDirections.actionColeccionesToListaColecciones(nombre))
    }
}
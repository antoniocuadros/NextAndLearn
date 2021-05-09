package com.example.nextandlearn.fragmentos

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R
import com.example.nextandlearn.fragmentos.adapters.ColeccionAdapter
import com.example.nextandlearn.modelo.Coleccion
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import java.util.*

/*
    Este fragmento dota de funcionalidad a la vista que muestra una lista de colecciones de palabras.
 */
class fragmentoListaVocabulario : Fragment() {
    /*
        Los atributos de esta clase son:
            -> adapter: Variable de tipo ColeccionAdapter que convertirá cada objeto de tipo Coleccion en un elemento de la lista de colecciones.
            -> colecciones: Variable de tipo MutableList<Coleccion> que contiene una lista de objetos de tipo Coleccion.
            -> cuadricula_coleccionVocabulario: Variable de tipo GridView que contiene el listado de colecciones.
            -> argumentos: Variable de tipo fragmentoListaVocabularioArgs utilizado para recibir datos desde otros fragmentos.
     */
    private lateinit var adapter:ColeccionAdapter
    private lateinit var colecciones:MutableList<Coleccion>
    private lateinit var cuadricula_coleccionVocabulario: GridView
    private val argumentos:fragmentoListaVocabularioArgs by navArgs()

    /*
        El método onCreate de cualquier Fragment es llamado cuando se crea inicialmente el fragmento,
        se llama al método onCreate de la clase superior, Fragment para crear el fragmento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*
    El método onCreateView de un fragmento crea y devuelve la jerarquía de la vista asociada con el
    fragmento.
    Adicionalmente de forma específica a este fragmento se realizan los siguientes pasos:
        -> Paso 1): Se infla y obtiene la vista
        -> Paso 2): Se inicializa el gridview que rellerá la vista con las colecciones.
        -> Paso 3): Se establece el clickLister de cada item de la lista de tal forma que se puede hacer click
        en aquellas colecciones las cuales han sido completadas las anteriores.
        -> Paso 4): Se devuelve la vista.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Paso 1
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_lista_vocabulario, container, false)

        // Paso 2
        //Inicializamos el gridView definiendo el adapter
        inicializaListaColecciones(view)

        // Paso 3
        //seteamos el click listener de las cartas que aparecen
        cuadricula_coleccionVocabulario.setOnItemClickListener { cuadricula_coleccionVocabulario, _, position, _ ->
            var coleccion:Coleccion = cuadricula_coleccionVocabulario.getItemAtPosition(position) as Coleccion

            //Si está completada podemos darle
            if(coleccion.completada == true){
                (activity as MainActivity).onColeccionSelected(coleccion.identificador)
            }
            else{
                if(position != 0){
                    if(colecciones[position-1].completada == true){
                        (activity as MainActivity).onColeccionSelected(coleccion.identificador)
                    }
                }
                else{
                    (activity as MainActivity).onColeccionSelected(coleccion.identificador)
                }
            }

        }

        // Paso 4
        return view
    }
    

    /*
        Este método se encarga de obtener las colecciones de la base de datos y adicionalmente se
        inicializa el adapter pasándole la lista de objetos Coleccion.
     */
    private fun inicializaListaColecciones(view:View){
        cuadricula_coleccionVocabulario = view.findViewById<GridView>(R.id.cuadricula_vocabulario)
        val db:VocabularioDataBase = obtenerBaseDatos(requireContext())

        if(argumentos.nombreColeccion != ""){
            colecciones = db.coleccionDao.obtenerColeccionSegunNivel(argumentos.nombreColeccion)
        }
        else{
            colecciones = db.coleccionDao.obtenerTodasColecciones()
        }


        adapter = ColeccionAdapter(colecciones, requireContext())
        cuadricula_coleccionVocabulario.adapter = adapter
    }

}
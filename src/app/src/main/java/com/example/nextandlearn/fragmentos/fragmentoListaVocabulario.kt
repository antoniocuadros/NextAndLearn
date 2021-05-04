package com.example.nextandlearn.fragmentos

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R
import com.example.nextandlearn.fragmentos.adapters.ColeccionAdapter
import com.example.nextandlearn.modelo.Coleccion
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import java.util.*


class fragmentoListaVocabulario : Fragment() {
    private lateinit var adapter:ColeccionAdapter
    private lateinit var colecciones:MutableList<Coleccion>
    private lateinit var cuadricula_coleccionVocabulario: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_lista_vocabulario, container, false)

        //Inicializamos el gridView definiendo el adapter
        inicializaListaColecciones(view)
        
        //seteamos el click listener de las cartas que aparecen
        cuadricula_coleccionVocabulario.setOnItemClickListener { cuadricula_coleccionVocabulario, _, position, _ ->
            var coleccion:Coleccion = cuadricula_coleccionVocabulario.getItemAtPosition(position) as Coleccion

            (activity as MainActivity).onColeccionSelected(coleccion.identificador)
        }

        return view
    }
    

    private fun inicializaListaColecciones(view:View){
        cuadricula_coleccionVocabulario = view.findViewById<GridView>(R.id.cuadricula_vocabulario)
        val db:VocabularioDataBase = obtenerBaseDatos(requireContext())
        colecciones = db.coleccionDao.obtenerTodasColecciones()

        adapter = ColeccionAdapter(colecciones, requireContext())
        cuadricula_coleccionVocabulario.adapter = adapter
    }

}
package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.navArgs
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R

class fragmentoMenuTestsVocabulario : Fragment() {
    private lateinit var boton_vocabulario:CardView
    private lateinit var boton_normal:RelativeLayout
    private lateinit var boton_listening:RelativeLayout
    private lateinit var boton_writing:RelativeLayout
    private lateinit var boton_speaking:RelativeLayout

    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_menu_tests_vocabulario, container, false)

        boton_normal = view.findViewById(R.id.boton_normal)
        boton_listening = view.findViewById(R.id.boton_listening)
        boton_writing = view.findViewById(R.id.boton_writing)
        boton_speaking = view.findViewById(R.id.boton_speaking)


        boton_vocabulario = view.findViewById(R.id.carta_voc)

        boton_vocabulario.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onVocabularioSelected(coleccion)
        }

        boton_normal.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onTestsSelected(coleccion, 1)
        }
        boton_listening.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onTestsSelected(coleccion, 2)
        }
        boton_writing.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onTestsSelected(coleccion,3)
        }
        boton_speaking.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onTestsSelected(coleccion, 4)
        }

        return view
    }
}
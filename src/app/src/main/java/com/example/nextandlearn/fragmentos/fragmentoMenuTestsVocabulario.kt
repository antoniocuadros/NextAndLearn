package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.navArgs
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R

class fragmentoMenuTestsVocabulario : Fragment() {
    private lateinit var boton_tests:CardView
    private lateinit var boton_vocabulario:CardView
    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_menu_tests_vocabulario, container, false)

        boton_tests = view.findViewById(R.id.carta_tests)
        boton_vocabulario = view.findViewById(R.id.carta_voc)

        boton_vocabulario.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onVocabularioSelected(coleccion)
        }

        boton_tests.setOnClickListener {
            var coleccion = argumentos.coleccion
            (activity as MainActivity).onTestsSelected(coleccion)
        }

        return view
    }
}
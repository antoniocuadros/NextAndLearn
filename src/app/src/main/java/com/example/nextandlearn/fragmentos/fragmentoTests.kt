package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase


class fragmentoTests : Fragment() {
    private lateinit var vocabulario:MutableList<Palabra>
    private lateinit var db:VocabularioDataBase
    private lateinit var enunciado:TextView
    private lateinit var imagen_opcion_1:ImageView
    private lateinit var text_opcion_1:TextView
    private lateinit var imagen_opcion_2:ImageView
    private lateinit var text_opcion_2:TextView
    private lateinit var imagen_opcion_3:ImageView
    private lateinit var text_opcion_3:TextView
    private lateinit var imagen_opcion_4:ImageView
    private lateinit var text_opcion_4:TextView
    private lateinit var boton_siguiente_test:Button

    private lateinit var boton_siguiente_acierto_fallo:Button
    private lateinit var texto_acierto_fallo:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragmento_tests, container, false)

        return view
    }

}
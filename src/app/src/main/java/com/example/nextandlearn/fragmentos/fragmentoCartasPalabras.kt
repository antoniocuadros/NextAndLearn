package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.nextandlearn.R
import com.example.nextandlearn.fragmentos.adapters.ListaVocabularioAdapter
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import me.relex.circleindicator.CircleIndicator3


class fragmentoCartasPalabras : Fragment() {
    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()
    private lateinit var slider_palabras: ViewPager2
    private lateinit var indicador_slider: CircleIndicator3
    private lateinit var palabras:MutableList<Palabra>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_cartas_palabras, container, false)


        //Obtenemos las vistas de los elementos
        slider_palabras = view.findViewById<ViewPager2>(R.id.slider_palabras_ly)
        indicador_slider = view.findViewById<CircleIndicator3>(R.id.indicador_slider)

        //Obtenemos las palabras asociadas a la colección pasada
        var db: VocabularioDataBase = obtenerBaseDatos(requireContext())
        palabras = db.palabraDao.obtenerPalabraSegunColeccion(argumentos.coleccion)

        //Definimos el adapter y le pasamos las palabras
        slider_palabras.adapter = ListaVocabularioAdapter(palabras, requireContext())
        slider_palabras.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicador_slider.setViewPager(slider_palabras)


        return view
    }

}
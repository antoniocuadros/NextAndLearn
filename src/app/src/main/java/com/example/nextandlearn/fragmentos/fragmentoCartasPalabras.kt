package com.example.nextandlearn.fragmentos

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.nextandlearn.R
import com.example.nextandlearn.fragmentos.adapters.ListaVocabularioAdapter
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import me.relex.circleindicator.CircleIndicator3
import java.util.*


class fragmentoCartasPalabras : Fragment(){
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
        //Adicionalmente definimos el clickListener
        slider_palabras.adapter = ListaVocabularioAdapter(palabras, requireContext()){palabra:Palabra, vista:View ->
            var palabra_espanol_vista =  vista.findViewById<TextView>(R.id.palabra_espanol)
            var boton_reproducir_vista = vista.findViewById<ImageButton>(R.id.boton_reproducir)

            //Estaba la palabra en inglés
            if(palabra_espanol_vista.text == palabra.ingles.capitalize()){
                palabra_espanol_vista.text = palabra.espanol.capitalize()
                boton_reproducir_vista.visibility = View.GONE
            }
            else{ //Estaba la palabra en español
                palabra_espanol_vista.text = palabra.ingles.capitalize()
                boton_reproducir_vista.visibility = View.VISIBLE
            }
        }
        slider_palabras.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        indicador_slider.setViewPager(slider_palabras)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
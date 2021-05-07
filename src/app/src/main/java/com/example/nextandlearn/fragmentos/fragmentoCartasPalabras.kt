package com.example.nextandlearn.fragmentos

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.LinearLayout
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.relex.circleindicator.CircleIndicator3
import java.util.*


class fragmentoCartasPalabras : Fragment(){
    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()
    private lateinit var slider_palabras: ViewPager2
    private lateinit var indicador_slider: CircleIndicator3
    private lateinit var palabras:MutableList<Palabra>
    private lateinit var animator:AnimatorSet
    private lateinit var animator2:AnimatorSet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_cartas_palabras, container, false)

        //Como este fragmento se reutiliza en dos pantallas, debemos cuidar que item marcamos
        //en el menú inferior
        var navegacion: BottomNavigationView = requireActivity().findViewById(R.id.menu_inferior);
        var menu_inferior: Menu = navegacion.getMenu();

        if(argumentos.coleccion == ""){
            var item_del_menu: MenuItem = menu_inferior.findItem(R.id.mi_baraja);
            item_del_menu.setChecked(true)
        }
        else{
            var item_del_menu: MenuItem = menu_inferior.findItem(R.id.listaVocabulario);
            item_del_menu.setChecked(true)
        }



        //Asignamos el valor al animator
        animator = AnimatorInflater.loadAnimator(context, R.animator.giro_carta_animator) as AnimatorSet
        animator2 = AnimatorInflater.loadAnimator(context, R.animator.giro_reverse_animator) as AnimatorSet

        //Obtenemos las vistas de los elementos
        slider_palabras = view.findViewById<ViewPager2>(R.id.slider_palabras_ly)
        indicador_slider = view.findViewById<CircleIndicator3>(R.id.indicador_slider)

        //Obtenemos las palabras asociadas a la colección pasada
        var db: VocabularioDataBase = obtenerBaseDatos(requireContext())
        if(argumentos.coleccion != ""){
            palabras = db.palabraDao.obtenerPalabraSegunColeccion(argumentos.coleccion)
        }
        else{
            palabras = db.palabraDao.obtenerPalabrasMarcadas(true)
            if(palabras.size == 0){
                palabras.add(Palabra("none","none","none","none",true))
            }
        }


        //Definimos el adapter y le pasamos las palabras
        //Adicionalmente definimos el clickListener
        slider_palabras.adapter = ListaVocabularioAdapter(palabras, requireContext()){palabra:Palabra, vista:View ->
            var palabra_espanol_vista =  vista.findViewById<TextView>(R.id.palabra_espanol)
            var boton_reproducir_vista = vista.findViewById<ImageButton>(R.id.boton_reproducir)

            //Estaba la palabra en inglés
            if(palabra_espanol_vista.text == palabra.ingles.capitalize()){
                animator.setTarget(vista)
                animator2.setTarget(vista)

                animator.start()
                animator2.start()

                palabra_espanol_vista.text = palabra.espanol.capitalize()
                boton_reproducir_vista.visibility = View.GONE
            }
            else{ //Estaba la palabra en español
                animator.setTarget(vista)
                animator2.setTarget(vista)
                animator.start()
                animator2.start()

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
package com.example.nextandlearn.fragmentos

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.TextView
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

/*
    Este fragmento dota de funcionalidad a la vista que muestra una lista de palabras en forma de cartas.
 */
class fragmentoCartasPalabras : Fragment(){
    /*
        Los atributos de esta clase son:
            -> argumentos: Variable de tipo fragmentoCartasPalabrasArgs utilizado para recibir datos desde otros fragmentos.
            -> slider_palabras: Variable de tipo ViewPager2 que contiene un slider de cartas que representan cada palabra.
            -> indicador_slider: Variable de tipo CircleIndicator3 que contiene un indicador del número de palabras.
            -> palabras: Variable de tipo MutableList<Palabra> que contiene la lista de objetos de tipo Palabra a mostrar.
            -> animator: Variable de tipo AnimatorSet utilizado para mostrar una animación de giro de carta.
            -> animator2: Variable de tipo AnimatorSet utilizado para mostrar una animación de giro de carta al revés una vez realizado el giro.
     */
    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()
    private lateinit var slider_palabras: ViewPager2
    private lateinit var indicador_slider: CircleIndicator3
    private lateinit var palabras:MutableList<Palabra>
    private lateinit var animator:AnimatorSet
    private lateinit var animator2:AnimatorSet
    private var modo = 0

    /*
    Se llama al método onCreate de la clase superior con el objetivo de crear el fragmento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /*
    Se realizan los siguientes pasos:
        -> 1) Se infla la vista.
        -> 2) Se comprueba desde donde venimos para ver que item del menú inferior debemos marcar.
              Es necesario comprobarlo ya que este fragmento se utiliza tanto en el apartado para
              repasar vocabulario como en el apartado mi baraja.
        -> 3) Se asignan los valores a los animators y a las vistas del slider y del indicador de palabras.
        -> 4) Se obtienen las palabras que se van a mostrar. Si no hay palabras a mostrar se añade una carta
              que indica que no hay palabras ahora mismo, útil para el apartado mi baraja.
        -> 5) Se inicializa el contenido de la vista.
        -> 6) Se devuelve la vista.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Paso 1
        val view =  inflater.inflate(R.layout.fragmento_cartas_palabras, container, false)

        // Paso 2
        var navegacion: BottomNavigationView = requireActivity().findViewById(R.id.menu_inferior);
        var menu_inferior: Menu = navegacion.getMenu();

        if(argumentos.coleccion == ""){
            var item_del_menu: MenuItem = menu_inferior.findItem(R.id.mi_baraja);
            item_del_menu.setChecked(true)
        }
        else{
            var item_del_menu: MenuItem = menu_inferior.findItem(R.id.colecciones);
            item_del_menu.setChecked(true)
        }


        //Paso 3
        //Asignamos el valor al animator
        animator = AnimatorInflater.loadAnimator(context, R.animator.giro_carta_animator) as AnimatorSet
        animator2 = AnimatorInflater.loadAnimator(context, R.animator.giro_reverse_animator) as AnimatorSet

        //Obtenemos las vistas de los elementos
        slider_palabras = view.findViewById<ViewPager2>(R.id.slider_palabras_ly)
        indicador_slider = view.findViewById<CircleIndicator3>(R.id.indicador_slider)


        // Paso 4
        obtenerPalabras()

        //Paso 5
        inicializaContenido()

        //Paso 6
        return view
    }

    /*
        Este método se encarga de obtener las palabras de la base de datos en función de la colección
        seleccionada. Si el string que identifica la colección que es recibido por parámetros en el fragmento
        no es vacío quiere decir que se ha seleccionado una colección y que venimos del fragmento de lista
        de colecciones, entonces mostramos las palabras de dicha colección. En caso de que el string sea vacío
        quiere decir que estamos en el apartado mi baraja y debemos obtener las palabras que se han marcado
        para ser añadidas a mi baraja.
     */
    private fun obtenerPalabras(){
        //Obtenemos las palabras asociadas a la colección pasada
        var db: VocabularioDataBase = obtenerBaseDatos(requireContext())
        if(argumentos.coleccion != ""){
            palabras = db.palabraDao.obtenerPalabraSegunColeccion(argumentos.coleccion)
            modo = 1
        }
        else{
            palabras = db.palabraDao.obtenerPalabrasMarcadas(true)
            if(palabras.size == 0){
                palabras.add(Palabra("none","none","none","none",true))
            }
        }
    }

    /*
        Este método se encarga de inicializar el adapter del slider pasándole el conjunto de palabras
        que se desean mostrar. Adicionalmente se establece el comportamiento del clicklistener de
        la carta que contiene el vocabulario. El comportamiento de este listener es mostrar o bien
        la palabra en inglés o en español en función del estado previo y añadir las animaciones.
     */
    private fun inicializaContenido(){
        //Definimos el adapter y le pasamos las palabras
        //Adicionalmente definimos el clickListener
        slider_palabras.adapter = ListaVocabularioAdapter(palabras, modo, requireContext()){palabra:Palabra, vista:View ->
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
    }
}
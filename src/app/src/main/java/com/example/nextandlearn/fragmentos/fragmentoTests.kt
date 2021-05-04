package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos


class fragmentoTests : Fragment() {
    private lateinit var vocabulario:MutableList<Palabra>
    private lateinit var db:VocabularioDataBase
    private var pregunta = 0

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

    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragmento_tests, container, false)

        //Inicializamos las vistas
        inicializa_vistas(view)

        //Obtenemos todas las palabras de la coleccion, se almacenan en la variable de clase
        obtener_palabras()

        return view
    }


    /*
        Con el identificador de la colección pasado por argumentos obtenemos de la base de datos
        todas las palabras de dicha colección para preparar las preguntas.
     */
    private fun obtener_palabras(){
        var identificacion_coleccion = argumentos.coleccion
        db = obtenerBaseDatos(requireContext())
        vocabulario = db.palabraDao.obtenerPalabraSegunColeccion(identificacion_coleccion)
    }

    /*
        Este método se encarga de vincular cada atributo de la clase con su vista correspondiente
        para posteriormente poder modificar la vista del test y ajustarlo a cada pregunta.
     */
    private fun inicializa_vistas(view:View){
        enunciado = view.findViewById(R.id.texto_enunciado)
        imagen_opcion_1 = view.findViewById(R.id.imagen_opcion1)
        text_opcion_1 = view.findViewById(R.id.texto_opcion1)
        imagen_opcion_2 = view.findViewById(R.id.imagen_opcion2)
        text_opcion_2 = view.findViewById(R.id.texto_opcion2)
        imagen_opcion_3 = view.findViewById(R.id.imagen_opcion3)
        text_opcion_3 = view.findViewById(R.id.texto_opcion3)
        imagen_opcion_4 = view.findViewById(R.id.imagen_opcion4)
        text_opcion_4 = view.findViewById(R.id.texto_opcion4)
        boton_siguiente_test = view.findViewById(R.id.boton_siguiente)

        boton_siguiente_acierto_fallo = view.findViewById(R.id.boton_siguiente2)
        texto_acierto_fallo = view.findViewById(R.id.mensaje_resultado)
    }
}
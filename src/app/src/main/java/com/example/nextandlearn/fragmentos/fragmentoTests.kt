package com.example.nextandlearn.fragmentos

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import androidx.navigation.fragment.navArgs
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import java.util.*
import kotlin.random.Random

/*
    Este fragmento dota de funcionalidad a la vista que muestra todos los tests de una determinada
    colección pudiendo ser de tipo Normal, Listening, Writing y Speaking.
 */
class fragmentoTests : Fragment(), TextToSpeech.OnInitListener {
    /*
        Los atributos de esta clase son:
            -> argumentos: Atributo de tipo fragmentoTestsArgs utilizado para recibir datos desde otros fragmentos.
            -> vocabulario: Atributo de tipo MutableList<Palabra> que contiene la lista de palabras que van a ser preguntadas en el test.
            -> db: Atributo de tipo VocabularioDataBase que nos permitirá acceder a la base de datos.
            -> pregunta: Atributo de tipo Int que nos indica por que pregunta del test va un determinado usuario respondiendo.
            -> numero_preguntas: Atributo de tipo Int que indica el número total de preguntas del test.
            -> opcion_elegida: Atributo de tipo Int indica la opción que el usuario ha seleccionado como correcta.
            -> aciertos: Atributo de tipo Int que representa número de aciertos acumulados en este intento del test.
            -> fallos: Atributo de tipo Int que representa número de fallos acumulados en este intento del test.
            -> opciones: Atributo de tipo MutableList<Palabra> lista de cuatro palabras que son opciones a elegir como correctas en un test, solo hay una correcta.
            -> reproductor: Atributo de tipo TextToSpeech que nos permite escuchar una determinada palabra en inglés para el test de tipo Listening.
            -> opcion: Atributo de tipo Int que indica que tipo de test se va a realizar, Normal, Listening, Writing, Speaking.
            -> enunciado: Atributo de tipo TextView  que representa el enunciado de la pregunta.
            -> imagen_opcion_1: Atributo de tipo ImageView que representa la imagen de la palabra de la opción 1.
            -> text_opcion_1: Atributo de tipo TextView que representa la palabra de la opción 1.
            -> imagen_opcion_2: Atributo de tipo ImageView que representa la imagen de la palabra de la opción 2.
            -> text_opcion_2: Atributo de tipo TextView que representa la palabra de la opción 2.
            -> imagen_opcion_3: Atributo de tipo ImageView que representa la imagen de la palabra de la opción 3.
            -> text_opcion_3: Atributo de tipo TextView que representa la palabra de la opción 3.
            -> imagen_opcion_4: Atributo de tipo ImageView que representa la imagen de la palabra de la opción 4.
            -> text_opcion_4: Atributo de tipo TextView que representa la palabra de la opción 4.
            -> boton_siguiente_test: Atributo de tipo Button que representa el botón para pasar a la siguiente pregunta.
            -> opcion_1: Atributo de tipo LinearLayout que es utilizado para asignarle un clickListener y seleccionar la opción 1.
            -> opcion_2: Atributo de tipo LinearLayout que es utilizado para asignarle un clickListener y seleccionar la opción 2.
            -> opcion_3: Atributo de tipo LinearLayout que es utilizado para asignarle un clickListener y seleccionar la opción 3.
            -> opcion_4: Atributo de tipo LinearLayout que es utilizado para asignarle un clickListener y seleccionar la opción 4.
            -> layout_test: Atributo de tipo LinearLayout que representa el layout del test completo, útil para ocultarlo y mostrar si una opción es o no correcta.
            -> layout_resultado: Atributo de tipo LinearLayout que representa el layout del resultado de una pregunta, útil para ocultarlo y mostrar la siguiente pregunta.
            -> num_aciertos_ly: Atributo de tipo TextView que es utilizado para mostrar el número de aciertos al terminar el test.
            -> num_fallos_ly:Atributo de tipo TextView que es utilizado para mostrar el número de fallos al terminar el test.
            -> boton_sonido: Atributo de tipo ImageButton que representa el botón para escuchar una palabra en inglés en el Listening.
            -> input_test: Atributo de tipo EditText que representa la caja donde escribir en el Writing.
            -> boton_grabar: Atributo de tipo ImageButton que representa el botón para capturar voz en el Speaking.
            -> texto_porcentaje_actual: Atributo de tipo TextView que representa en el text actual el porcentaje de aciertos.
            -> texto_porcentaje: Atributo de tipo TextView que representa en el conjunto de todos los tests de este tipo en esta colección el porcentaje de aciertos.
            -> boton_siguiente_acierto_fallo: Atributo de tipo Button que es utilizado para pasar a la siguiente pregunta desde la pantalla de acierto o fallo.
            -> texto_acierto_fallo: Atributo de tipo TextView que indica si hemos acertado o fallado la pregunta.
     */
    private val argumentos:fragmentoTestsArgs by navArgs()
    private lateinit var vocabulario:MutableList<Palabra>
    private lateinit var db:VocabularioDataBase
    private var pregunta = 0
    private var numero_preguntas = 0
    private lateinit var opcion_elegida:Palabra
    private var aciertos = 0
    private var fallos = 0
    private lateinit var opciones: MutableList<Palabra>
    private lateinit var reproductor:TextToSpeech
    private var opcion = 0

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
    private lateinit var opcion_1:LinearLayout
    private lateinit var opcion_2:LinearLayout
    private lateinit var opcion_3:LinearLayout
    private lateinit var opcion_4:LinearLayout
    private lateinit var layout_test:LinearLayout
    private lateinit var layout_resultado:LinearLayout
    private lateinit var num_aciertos_ly:TextView
    private lateinit var num_fallos_ly:TextView
    private lateinit var boton_sonido:ImageButton
    private lateinit var input_test:EditText
    private lateinit var boton_grabar:ImageButton
    private lateinit var texto_porcentaje_actual:TextView
    private lateinit var texto_porcentaje:TextView
    private lateinit var carta1:CardView
    private lateinit var carta2:CardView
    private lateinit var carta3:CardView
    private lateinit var carta4:CardView
    private lateinit var fondocarta1:CardView
    private lateinit var fondocarta2:CardView
    private lateinit var fondocarta3:CardView
    private lateinit var fondocarta4:CardView
    private lateinit var img1:ImageView

    private lateinit var boton_siguiente_acierto_fallo:Button
    private lateinit var texto_acierto_fallo:TextView


    /*
        Se llama al método onCreate de la clase superior con el objetivo de crear el fragmento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /*
    Se realizan los siguientes pasos:
        -> Paso 1): Se infla y obtiene la vista
        -> Paso 2): Se obtiene el tipo de test a realizar y se almacena que tipo de test se va a realizar.
        -> Paso 3): Se vinculan las vistas con los atributos correspondientes.
        -> Paso 4): Se obtienen las palabras que van a ser preguntadas en el test.
        -> Paso 5): Se obtienen las posibles opciones para la pregunta actual y el enunciado, solo una correcta.
        -> Paso 6): Se añaden las opciones a la vista.
        -> Paso 7): Se añaden los listeners para que el usuario pueda hacer click
        -> Paso 8): Se gestiona el botón para pasar a la siguiente pregunta que comprobará si se ha acertado o fallado
        -> Paso 9): Se devuelve la vista.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Paso 1
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragmento_tests, container, false)

        // Paso 2
        opcion = argumentos.opcion
        reproductor = TextToSpeech(context, this)

        // Paso 3
        //Inicializamos las vistas
        inicializa_vistas(view)

        // Paso 4
        //Obtenemos todas las palabras de la coleccion, se almacenan en la variable de clase
        obtener_palabras()

        // Paso 5
        //Obtenemos las opciones
        opciones = generaOpciones()

        // Paso 6
        //Las añadimos a la vista
        anadeOpcionesVista()

        // Paso 7
        // Añadimos los listeners de los botones de las opciones
        anadeListenersOpciones()

        // Paso 8
        // Gestionamos el comportamiento del botón siguiente
        gestionaBotonSiguiente()

        // Paso 9
        return view
    }

    /*
        Este método se encarga de cambiar de color al pulsar sobre cada uno de los elementos,
        desmarca la opción anterior y marca la nueva.
     */
    private fun cambiaColorSeleccionada(opcion:Int){
        when(opcion){
            1 -> {
                opcion_1.setBackgroundColor(Color.parseColor("#B165D4"))
                opcion_2.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_3.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_4.setBackgroundColor(Color.parseColor("#E0BBE4"))
            }
            2 -> {
                opcion_1.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_2.setBackgroundColor(Color.parseColor("#B165D4"))
                opcion_3.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_4.setBackgroundColor(Color.parseColor("#E0BBE4"))
            }
            3 -> {
                opcion_1.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_2.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_3.setBackgroundColor(Color.parseColor("#B165D4"))
                opcion_4.setBackgroundColor(Color.parseColor("#E0BBE4"))
            }
            4 -> {
                opcion_1.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_2.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_3.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_4.setBackgroundColor(Color.parseColor("#B165D4"))
            }
            5 -> {
                opcion_1.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_2.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_3.setBackgroundColor(Color.parseColor("#E0BBE4"))
                opcion_4.setBackgroundColor(Color.parseColor("#E0BBE4"))
            }
        }
    }

    /*
        Este método se encarga de gestionar el botón de siguiente de la pestaña acierto o fallo
        Tenemos que formular una nueva pregunta
     */
    private fun gestionaSiguienteAciertoFallo(){
        boton_siguiente_acierto_fallo.setOnClickListener{
            if(boton_siguiente_acierto_fallo.text == "Finalizar"){ //Hemos terminado, vamos a la pantalla de colecciones
                (activity as MainActivity).fromTestsToColecciones()
            }
            else{ //Generamos las siguientes
                if(pregunta < numero_preguntas-1){
                    pregunta++
                    opciones = generaOpciones()
                    anadeOpcionesVista()

                    layout_test.visibility = View.VISIBLE
                    layout_resultado.visibility = View.GONE

                    if(pregunta == numero_preguntas-1){ //Es la última pregunta, mostramos resultados
                        num_fallos_ly.visibility = View.VISIBLE
                        num_aciertos_ly.visibility = View.VISIBLE
                        num_aciertos_ly.text = "Aciertos: " + aciertos
                        num_fallos_ly.text = "Fallos: " + fallos

                        boton_siguiente_acierto_fallo.text = "Finalizar"

                    }
                }
                else{
                    boton_siguiente_acierto_fallo.text = "Finalizar"
                }
            }

        }
    }

    /*
        Este método se encarga de calcular los valores de los porcentajes obtenidos previamente
        y en este intento
     */
    private fun obtenerPorcentajes():MutableList<Int>{
        var col = db.coleccionDao.obtenerColeccionSegunIdentificador(argumentos.coleccion)
        var porcentaje_actual = 0
        var porcentaje_global = 0
        var devolver:MutableList<Int> = mutableListOf()

        when(opcion){
            1->{
                porcentaje_actual = (aciertos * 100) / numero_preguntas
                porcentaje_global = (col[0].puntos_normal * 100) / numero_preguntas
            }
            2->{
                porcentaje_actual = (aciertos * 100) / numero_preguntas
                porcentaje_global = (col[0].puntos_listening * 100) / numero_preguntas
            }
            3->{
                porcentaje_actual = (aciertos * 100) / numero_preguntas
                porcentaje_global = (col[0].puntos_writing * 100) / numero_preguntas
            }
            4->{
                porcentaje_actual = (aciertos * 100) / numero_preguntas
                porcentaje_global = (col[0].puntos_speaking * 100) / numero_preguntas
            }
        }
        devolver.add(porcentaje_actual)
        devolver.add(porcentaje_global)

        return devolver
    }

    /*
        Este método se encarga de gestionar los aciertos y fallos
     */
    private fun gestionaAciertoFallo(acierto:Boolean){
        layout_test.visibility = View.GONE
        layout_resultado.visibility = View.VISIBLE
        if(acierto){
            texto_acierto_fallo.text = "¡Acierto!"

            aciertos++
        }
        else{
            texto_acierto_fallo.text = "Fallo"

            fallos++
        }
        if(pregunta == numero_preguntas-1){
            if(aciertos >= (numero_preguntas / 2)){ //Ha terminado los tests correctamente
                texto_acierto_fallo.text = "¡Aprobado!"
                num_aciertos_ly.text = "Aciertos: " + aciertos
                num_fallos_ly.text = "Fallos: " + fallos

                var porcentajes:MutableList<Int>
                porcentajes = obtenerPorcentajes()
                var texto1 = "Puntuación máxima: " + porcentajes[1].toString()
                var texto2 = "Puntuación actual: " + porcentajes[0].toString()
                texto_porcentaje.text = texto1
                texto_porcentaje_actual.text = texto2

                texto_acierto_fallo.textSize = 40.0F
                boton_siguiente_acierto_fallo.text = "Finalizar"

                //Marcamos como aprobado el test
                var coleccion_completada = db.coleccionDao.obtenerColeccionSegunIdentificador(argumentos.coleccion)

                when(opcion){
                    1 ->{
                        coleccion_completada[0].completado_normal = true

                        if(aciertos > coleccion_completada[0].puntos_normal){
                            coleccion_completada[0].puntos_normal = aciertos
                        }
                    }
                    2 ->{
                        coleccion_completada[0].completado_listening = true

                        if(aciertos > coleccion_completada[0].puntos_listening){
                            coleccion_completada[0].puntos_listening = aciertos
                        }
                    }
                    3 ->{
                        coleccion_completada[0].completado_writing = true

                        if(aciertos > coleccion_completada[0].puntos_writing){
                            coleccion_completada[0].puntos_writing = aciertos
                        }
                    }
                    4 ->{
                        coleccion_completada[0].completado_listening = true

                        if(aciertos > coleccion_completada[0].puntos_listening){
                            coleccion_completada[0].puntos_listening = aciertos
                        }
                    }
                }

                if(coleccion_completada[0].completado_normal && coleccion_completada[0].completado_listening && coleccion_completada[0].completado_writing){
                    coleccion_completada[0].completada = true
                }

                db.coleccionDao.actualizaColeccion(coleccion_completada[0])
            }
            else { //No ha terminado los tests por tener 2 o más fallos
                texto_acierto_fallo.text = "Suspenso"
                texto_acierto_fallo.textSize = 40.0F
                num_aciertos_ly.text = "Aciertos: " + aciertos
                num_fallos_ly.text = "Fallos: " + fallos
            }
        }

        var porcentajes:MutableList<Int>
        porcentajes = obtenerPorcentajes()
        var texto1 = "Puntuación máxima: " + porcentajes[1].toString() + "%"
        var texto2 = "Puntuación actual: " + porcentajes[0].toString() + "%"
        texto_porcentaje.text = texto1
        texto_porcentaje_actual.text = texto2


        gestionaSiguienteAciertoFallo()
    }

    /*
    Este método define el listener del botón siguiente de cada test y su comportamiento
     */
    private fun gestionaBotonSiguiente(){
        boton_siguiente_test.setOnClickListener{
            //Caso de la opción 3, recuperamos aquí el texto introducido
            if(opcion == 3) {
                var palabra_escrita = input_test.text.toString()
                var resultado: MutableList<Palabra> = (db.palabraDao.obtenerPalabraSegunIngles(palabra_escrita.decapitalize()))
                if (resultado.size > 0) {
                    opcion_elegida = resultado[0]
                } else {
                    opcion_elegida = Palabra("palabra_escrita", "palabra_escrita", "none", "none", false)
                }
                input_test.text.clear()
            }

            //Comprobamos si es acierto o fallo
            if(opcion_elegida.ingles == "none"){
                Toast.makeText(context, "Seleccione primero una opción", Toast.LENGTH_SHORT).show()
            }
            else{
                if(opcion_elegida == vocabulario[pregunta]){
                    gestionaAciertoFallo(true)
                }
                else{
                    gestionaAciertoFallo(false)
                }
                opcion_elegida = Palabra("none","none", "none","none",false)
                cambiaColorSeleccionada(5)
            }
        }
    }

    /*
        Este método se encarga de definir los listener de cada una de las cuatro opciones posibles
        y almacena el valor elegido en una variable de la clase para su posterior tratamiento.
     */
    private fun anadeListenersOpciones(){
        opcion_1.setOnClickListener {
            opcion_elegida = opciones[0]
            cambiaColorSeleccionada(1)
        }

        opcion_2.setOnClickListener {
            opcion_elegida = opciones[1]
            cambiaColorSeleccionada(2)
        }

        opcion_3.setOnClickListener {
            opcion_elegida = opciones[2]
            cambiaColorSeleccionada(3)
        }

        opcion_4.setOnClickListener {
            opcion_elegida = opciones[3]
            cambiaColorSeleccionada(4)
        }

        boton_grabar.setOnClickListener{
            grabacionDisponible()
        }
    }


    /*
        Este método se encarga de comprobar si es correcta o no la respuesta
     */
    private fun opcionSeleccionada(palabra:Palabra):Boolean{
        return palabra.ingles == vocabulario[pregunta].ingles
    }

    /*
        Este método se encarga de dado un conjunto de opciones añadirlas a la vista para que se muestren
        junto a un enunciado formando una pregunta del test
     */
    private fun anadeOpcionesVista(){
        var identificador_imagen:Int

        /*
            En función del tipo de pregunta se hacen unas cosas u otras
         */
        when(opcion){
            1 ->{ //Tipo 1, se pone una palabra en español y las opciones en inglés
                enunciado.text = "¿Cómo es " + vocabulario[pregunta].espanol.capitalize() + " en inglés?"
            }
            2 ->{ //Tipo 2, listening, se da una reproducción de una palabra y se debe seleccionar la opción en inglés
                enunciado.text = "Seleccione la palabra que se reproduce"
                boton_sonido.visibility = View.VISIBLE
                boton_sonido.setOnClickListener {
                    reproductor.speak(vocabulario[pregunta].ingles, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
            3 ->{ //Tipo 3 writting, se da una palabra en español y se debe escribir en inglés.
                enunciado.text = "¿Cómo se escribe " + vocabulario[pregunta].espanol + " en inglés?"
                opcion_1.visibility = View.GONE
                opcion_2.visibility = View.GONE
                opcion_3.visibility = View.GONE
                opcion_4.visibility = View.GONE

                input_test.visibility = View.VISIBLE
            }
            4->{
                enunciado.text = "¿Cómo se pronuncia " + vocabulario[pregunta].espanol + " en inglés?"
                opcion_1.visibility = View.GONE
                opcion_2.visibility = View.GONE
                opcion_3.visibility = View.GONE
                opcion_4.visibility = View.GONE

                boton_grabar.visibility = View.VISIBLE
            }
        }




        if(opcion != 1){
            identificador_imagen = context?.resources?.getIdentifier(opciones[0].imagen, "drawable", "com.example.nextandlearn")!!
            imagen_opcion_1.setImageResource(identificador_imagen)

            identificador_imagen = context?.resources?.getIdentifier(opciones[2].imagen, "drawable", "com.example.nextandlearn")!!
            imagen_opcion_2.setImageResource(identificador_imagen)

            identificador_imagen = context?.resources?.getIdentifier(opciones[1].imagen, "drawable", "com.example.nextandlearn")!!
            imagen_opcion_3.setImageResource(identificador_imagen)

            identificador_imagen = context?.resources?.getIdentifier(opciones[3].imagen, "drawable", "com.example.nextandlearn")!!
            imagen_opcion_4.setImageResource(identificador_imagen)

            text_opcion_1.text = opciones[0].ingles.capitalize()
            text_opcion_2.text = opciones[2].ingles.capitalize()
            text_opcion_3.text = opciones[1].ingles.capitalize()
            text_opcion_4.text = opciones[3].ingles.capitalize()

        }
        else{
            identificador_imagen = context?.resources?.getIdentifier(vocabulario[pregunta].imagen, "drawable", "com.example.nextandlearn")!!
            //imagen_opcion_1.setImageResource(identificador_imagen)
            //imagen_opcion_2.setImageResource(identificador_imagen)
            //imagen_opcion_3.setImageResource(identificador_imagen)
            //imagen_opcion_4.setImageResource(identificador_imagen)
            carta1!!.visibility = View.GONE
            carta2!!.visibility = View.GONE
            carta3!!.visibility = View.GONE
            carta4!!.visibility = View.GONE

            text_opcion_1.text = opciones[0].ingles.capitalize()
            var margin1 = fondocarta1.layoutParams as LinearLayout.LayoutParams
            margin1.setMargins(30,30,30,30)

            text_opcion_2.text = opciones[2].ingles.capitalize()
            var margin2 = fondocarta2.layoutParams as LinearLayout.LayoutParams
            margin2.setMargins(30,30,30,30)
            fondocarta2.setPadding(40,40,40,40)

            text_opcion_3.text = opciones[1].ingles.capitalize()
            var margin3 = fondocarta3.layoutParams as LinearLayout.LayoutParams
            margin3.setMargins(30,30,30,30)
            fondocarta3.setPadding(40,40,40,40)

            text_opcion_4.text = opciones[3].ingles.capitalize()
            var margin4 = fondocarta4.layoutParams as LinearLayout.LayoutParams
            margin4.setMargins(30,30,30,30)
            fondocarta4.setPadding(40,40,40,40)

            identificador_imagen = context?.resources?.getIdentifier(vocabulario[pregunta].imagen, "drawable", "com.example.nextandlearn")!!
            img1.setImageResource(identificador_imagen)
            img1.visibility = View.VISIBLE
        }




    }

    /*
        Esta función genera las opciones de la pregunta que se va a mostrar
     */
    private fun generaOpciones():MutableList<Palabra>{
        //Obtenemos la palabra que va a ser preguntada
        var palabra_pregunta = vocabulario[pregunta]

        //Por defecto a la opcion elegida
        opcion_elegida = Palabra("none","none", "none","none",false)

        //Ahora 'aleatoriamente' cogemos tres palabras distintas a la anterior
        //La estrategia para elegir las palabras de forma sencilla es coger
        //a parte las tres siguientes palabras (de forma circular para no salirnos
        //del array).
        var a_coger = 3
        var vector_opciones:MutableList<Palabra> = mutableListOf()

        while(a_coger > 0){
            vector_opciones.add(vocabulario[(pregunta + a_coger) % numero_preguntas])
            a_coger--
        }

        //Anadimos la correcta
        vector_opciones.add(palabra_pregunta)

        //Deberíamos mezclar el vector para que la correcta no sea siempre la primera
        var a_devolver:MutableList<Palabra> = mutableListOf()
        for(i in 1..4){
            var random = 0
            if(vector_opciones.size > 0){
                random = Random.nextInt(0,vector_opciones.size)
            }
            a_devolver.add(vector_opciones[random])
            vector_opciones.removeAt(random)
        }

        //Devolvemos el vector mezclado
        return a_devolver
    }

    /*
        Con el identificador de la colección pasado por argumentos obtenemos de la base de datos
        todas las palabras de dicha colección para preparar las preguntas.
     */
    private fun obtener_palabras(){
        var identificacion_coleccion = argumentos.coleccion
        db = obtenerBaseDatos(requireContext())
        vocabulario = db.palabraDao.obtenerPalabraSegunColeccion(identificacion_coleccion)
        numero_preguntas = vocabulario.size
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
        opcion_1 = view.findViewById(R.id.opcion1)
        opcion_2 = view.findViewById(R.id.opcion2)
        opcion_3 = view.findViewById(R.id.opcion3)
        opcion_4 = view.findViewById(R.id.opcion4)
        layout_test = view.findViewById(R.id.layout_test)
        layout_resultado = view.findViewById(R.id.layout_resultado)
        num_aciertos_ly = view.findViewById(R.id.aciertos_ly)
        num_fallos_ly = view.findViewById(R.id.fallos_ly)
        boton_sonido = view.findViewById(R.id.boton_reproducir)
        input_test = view.findViewById(R.id.input_test)
        boton_grabar = view.findViewById(R.id.boton_grabar)
        texto_porcentaje_actual = view.findViewById(R.id.texto_porcentaje_actual)
        texto_porcentaje= view.findViewById(R.id.texto_porcentaje)
        carta1 = view?.findViewById<CardView>(R.id.carta1)
        carta2 = view?.findViewById<CardView>(R.id.carta2)
        carta3 = view?.findViewById<CardView>(R.id.carta3)
        carta4 = view?.findViewById<CardView>(R.id.carta4)
        img1 = view?.findViewById<ImageView>(R.id.imagen_sola)
        fondocarta1 = view?.findViewById<CardView>(R.id.fondotexto1)
        fondocarta2 = view?.findViewById<CardView>(R.id.fondotexto2)
        fondocarta3 = view?.findViewById<CardView>(R.id.fondotexto3)
        fondocarta4 = view?.findViewById<CardView>(R.id.fondotexto4)


        boton_siguiente_acierto_fallo = view.findViewById(R.id.boton_siguiente2)
        texto_acierto_fallo = view.findViewById(R.id.mensaje_resultado)
    }

    /*
        Los dos métodos siguientes tienen que ver con permitir la grabación de sonido
     */
    /*
        Este método captura el código de la grabación y ve si es correcto
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            var texto_hablado = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            var palabra_escrita = texto_hablado?.get(0).toString()
            Toast.makeText(context, palabra_escrita.toString(), Toast.LENGTH_SHORT).show()
            var resultado: MutableList<Palabra> = (db.palabraDao.obtenerPalabraSegunIngles(palabra_escrita.decapitalize()))
            if (resultado.size > 0) {
                opcion_elegida = resultado[0]
            } else {
                opcion_elegida = Palabra("palabra_escrita", "palabra_escrita", "none", "none", false)
            }


        }
    }

    /*
        Este método se encarga de comprobar si el micrófono está disponible, si no lo está
        muestra un mensaje al usuario y si lo está configuramos el idioma de la grabadora, en
        este caso inglés.
     */
    private fun grabacionDisponible(){
        if(!SpeechRecognizer.isRecognitionAvailable((context))){
            Toast.makeText(context, "El micrófono no está disponible", Toast.LENGTH_SHORT).show()
        }
        else{
            var intent_grabar = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent_grabar.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent_grabar.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK)
            startActivityForResult(intent_grabar, 100)
        }
    }

    /*
        Sobrecarga del método de la clase TextToSpeech utilizado para definir el idioma en el que se
        va a capturar la voz.
     */
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            reproductor.language = Locale.UK
        }
    }
}
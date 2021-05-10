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
import com.example.nextandlearn.modelo.obtenerBaseDatos
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
/*
    Este fragmento dota de funcionalidad a la vista que muestra un menu donde se da a elegir entre ver el
    vocabulario de una colección o hacer los tests, adicionalmente se muestra información del progreso
    en los tests.
 */
class fragmentoMenuTestsVocabulario : Fragment() {
    /*
       Los atributos de esta clase son:
           -> boton_vocabulario: Atributo de tipo CardView que representa el icono donde hacer click para ver el vocabulario de la colección.
           -> boton_normal: Atributo de tipo RelativeLayout que representa el botón para dirigirnos a los tests de tipo Normal de la colección.
           -> boton_listening: Atributo de tipo RelativeLayout que representa el botón para dirigirnos a los tests de tipo Listening de la colección.
           -> boton_writing: Atributo de tipo RelativeLayout que representa el botón para dirigirnos a los tests de tipo Writing de la colección.
           -> boton_speaking: Atributo de tipo RelativeLayout que representa el botón para dirigirnos a los tests de tipo Writing de la colección.
           -> grafico: Atributo de tipo AAChartView que representa el gráfico donde se muestra el progreso en los tests de dicha colección.
           -> argumentos: Atributo de tipo fragmentoCartasPalabrasArgs  utilizado para recibir datos desde otros fragmentos.
    */
    private lateinit var boton_vocabulario:CardView
    private lateinit var boton_normal:RelativeLayout
    private lateinit var boton_listening:RelativeLayout
    private lateinit var boton_writing:RelativeLayout
    private lateinit var boton_speaking:RelativeLayout
    private lateinit var grafico:AAChartView
    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()

    /*
     Se llama al método onCreate de la clase superior con el objetivo de crear el fragmento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*
    Se realizan los siguientes pasos:
        -> Paso 1): Se infla y obtiene la vista
        -> Paso 2): Se vinculan los atributos relacionados con vistas con las vistas correspondientes.
        -> Paso 3): Se añaden los listeners de los botones para tests y vocabulario.
        -> Paso 4): Se añade el gráfico relacionado con los tests y el porcentaje completado.
        -> Paso 5): Se devuelve la vista
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Paso 1
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_menu_tests_vocabulario, container, false)

        //Paso 2
        //Vinculamos las vistas con las variables
        inicializaVistas(view)

        //Paso 3
        //Añadimos los listeners de los botones
        anadeListenersBotonoes()

        //Paso 4
        //Añadimos el gráfico
        anadeGrafico()

        //Paso 5
        return view
    }

    private fun inicializaVistas(view:View){
        boton_normal = view.findViewById(R.id.boton_normal)
        boton_listening = view.findViewById(R.id.boton_listening)
        boton_writing = view.findViewById(R.id.boton_writing)
        boton_speaking = view.findViewById(R.id.boton_speaking)
        boton_vocabulario = view.findViewById(R.id.carta_voc)
        grafico = view.findViewById(R.id.grafico)
    }

    /*
        Este método se encarga de vincular cada atributo de la clase de tipo vista
        con su vista correspondiente.
     */
    private fun anadeListenersBotonoes(){
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
    }

    /*
        Este método se encarga de añadir un gráfico que nos indica que porcentaje de cada tipo de test
        se ha conseguido como máximo hasta el momento. Para ello se obtiene la colección, de la colección
        calculamos los porcentajes de aciertos de cada test y por último se define el gráfico con los
        datos calculados.
     */
    private fun anadeGrafico(){
        var db = obtenerBaseDatos(requireContext())
        var coleccion_obtenida = db.coleccionDao.obtenerColeccionSegunIdentificador(argumentos.coleccion)

        var porcentajeNormal = (coleccion_obtenida[0].puntos_normal * 100) / db.palabraDao.obtenerNumPalabrasColeccion(argumentos.coleccion)
        var porcentajeListening = (coleccion_obtenida[0].puntos_listening * 100) / db.palabraDao.obtenerNumPalabrasColeccion(argumentos.coleccion)
        var porcentajeWriting = (coleccion_obtenida[0].puntos_writing * 100) / db.palabraDao.obtenerNumPalabrasColeccion(argumentos.coleccion)
        var porcentajeSpeaking = (coleccion_obtenida[0].puntos_speaking * 100) / db.palabraDao.obtenerNumPalabrasColeccion(argumentos.coleccion)

        val modelo_grafico:AAChartModel = AAChartModel().chartType(AAChartType.Column).title("Porcentaje aciertos tests")
            .dataLabelsEnabled(true).touchEventEnabled(false).yAxisTitle("").yAxisMax(100F)
            .series(arrayOf(
                AASeriesElement()
                    .name("Normal")
                    .data(arrayOf(porcentajeNormal))
                    .color("#00b050"),
                AASeriesElement()
                    .name("Listening")
                    .data(arrayOf(porcentajeListening))
                    .color("#0099ff"),
                AASeriesElement()
                    .name("Writing")
                    .data(arrayOf(porcentajeWriting))
                    .color("#7030a0"),
                AASeriesElement()
                    .name("Speaking")
                    .data(arrayOf(porcentajeSpeaking))
                    .color("#009999")
            ))
        grafico.aa_drawChartWithChartModel(modelo_grafico)
    }
}
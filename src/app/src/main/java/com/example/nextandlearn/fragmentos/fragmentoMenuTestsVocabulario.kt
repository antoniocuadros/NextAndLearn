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

class fragmentoMenuTestsVocabulario : Fragment() {
    private lateinit var boton_vocabulario:CardView
    private lateinit var boton_normal:RelativeLayout
    private lateinit var boton_listening:RelativeLayout
    private lateinit var boton_writing:RelativeLayout
    private lateinit var boton_speaking:RelativeLayout
    private lateinit var grafico:AAChartView

    private val argumentos:fragmentoCartasPalabrasArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragmento_menu_tests_vocabulario, container, false)


        //Vinculamos las vistas con las variables
        inicializaVistas(view)

        //Añadimos los listeners de los botones
        anadeListenersBotonoes()

        //Añadimos el gráfico
        anadeGrafico()

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
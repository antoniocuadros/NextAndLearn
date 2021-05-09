package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.activity.addCallback
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R
import com.example.nextandlearn.fragmentos.adapters.ColeccionAdapter
import com.example.nextandlearn.fragmentos.adapters.NivelAdapter
import com.example.nextandlearn.modelo.Coleccion
import com.example.nextandlearn.modelo.Nivel
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import kotlin.system.exitProcess
/*
    Este fragmento dota de funcionalidad a la vista que muestra una lista con todos los niveles
    de vocabulario con los que cuenta nuestra aplicación.
 */
class fragmentoNiveles : Fragment() {
    /*
       Los atributos de esta clase son:
           -> adapter: Atributo de tipo NivelAdapter que convertirá cada objeto de tipo Nivel en un elemento de la lista de niveles.
           -> niveles: Atributo de tipo MutableList<Nivel> que contiene una lista de objetos de tipo Nivel.
           -> cuadricula_niveles: Atributo de tipo GridView que contiene el listado de niveles.
    */
    private lateinit var adapter: NivelAdapter
    private lateinit var niveles:MutableList<Nivel>
    private lateinit var cuadricula_niveles: GridView

    /*
        El método onCreate de cualquier Fragment es llamado cuando se crea inicialmente el fragmento,
        se llama al método onCreate de la clase superior, Fragment para crear el fragmento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            exitProcess(0)
        }
    }

    /*
    El método onCreateView de un fragmento crea y devuelve la jerarquía de la vista asociada con el
    fragmento.
    Adicionalmente de forma específica a este fragmento se realizan los siguientes pasos:
        -> Paso 1): Se infla y obtiene la vista
        -> Paso 2): Se inicializa el gridview que rellerá la vista con los niveles.
        -> Paso 3): Se establece el clickLister de cada item de la lista de tal forma que se puede hacer click
        en los niveles para redigirnos al fragmento que contiene las colecciones de dicho nivel.
        -> Paso 4): Se devuelve la vista.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Paso 1
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragmento_niveles, container, false)

        //Paso 2
        inicializaListaNiveles(view)

        //Paso 3
        cuadricula_niveles.setOnItemClickListener{cuadricula, _, position, _ ->
            var nivel:Nivel = cuadricula.getItemAtPosition(position) as Nivel

            (activity as MainActivity).fromNivelesToColecciones(nivel.nombre)
        }

        //Paso 4
        return view
    }

    /*
        Este método se encarga de obtener los niveles de la base de datos y adicionalmente se
        inicializa el adapter pasándole la lista de objetos Nivel.
     */
    private fun inicializaListaNiveles(view:View){
        cuadricula_niveles = view.findViewById<GridView>(R.id.cuadricula_niveles)
        val db: VocabularioDataBase = obtenerBaseDatos(requireContext())
        niveles = db.nivelDao.obtenerTodosNiveles()

        adapter = NivelAdapter(niveles, requireContext())
        cuadricula_niveles.adapter = adapter
    }

}
package com.example.nextandlearn.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.nextandlearn.MainActivity
import com.example.nextandlearn.R
import com.example.nextandlearn.fragmentos.adapters.ColeccionAdapter
import com.example.nextandlearn.fragmentos.adapters.NivelAdapter
import com.example.nextandlearn.modelo.Coleccion
import com.example.nextandlearn.modelo.Nivel
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos

class fragmentoNiveles : Fragment() {
    private lateinit var adapter: NivelAdapter
    private lateinit var niveles:MutableList<Nivel>
    private lateinit var cuadricula_niveles: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragmento_niveles, container, false)

        inicializaListaNiveles(view)

        cuadricula_niveles.setOnItemClickListener{cuadricula, _, position, _ ->
            var nivel:Nivel = cuadricula.getItemAtPosition(position) as Nivel

            (activity as MainActivity).fromNivelesToColecciones(nivel.nombre)
        }


        return view
    }

    private fun inicializaListaNiveles(view:View){
        cuadricula_niveles = view.findViewById<GridView>(R.id.cuadricula_niveles)
        val db: VocabularioDataBase = obtenerBaseDatos(requireContext())
        niveles = db.nivelDao.obtenerTodosNiveles()

        adapter = NivelAdapter(niveles, requireContext())
        cuadricula_niveles.adapter = adapter
    }

}
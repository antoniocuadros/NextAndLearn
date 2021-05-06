package com.example.nextandlearn.fragmentos.adapters

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import org.w3c.dom.Text
import java.util.*


class ListaVocabularioAdapter(var palabras: MutableList<Palabra>, context: Context, val clickListener: (Palabra, View) -> Unit): RecyclerView.Adapter<ListaVocabularioAdapter.Pager2ViewHolder>(), TextToSpeech.OnInitListener{
    private var context = context
    var reproductor = TextToSpeech(context, this)

    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val  imagen = itemView.findViewById<ImageView>(R.id.imagen_palabra)
        val espanol = itemView.findViewById<TextView>(R.id.palabra_espanol)
        val boton = itemView.findViewById<ImageButton>(R.id.boton_anadir2)
        val boton_reproducir = itemView.findViewById<ImageButton>(R.id.boton_reproducir)
        val db:VocabularioDataBase = obtenerBaseDatos(context)


        //Para poder hacer click en los elementos
        init{
            itemView.setOnClickListener{
                clickListener(palabras[adapterPosition], it)
            }

            //Listener del botón que añade a la lista de palabras a recordar
            boton.setOnClickListener{
                var palabra = palabras[adapterPosition]
                palabra.marcada = true
                db.palabraDao.actualizaPalabra(palabra)
                boton.visibility = View.GONE
            }



            boton_reproducir.setOnClickListener {
                var palabra = palabras[adapterPosition]
                reproductor.speak(palabra.ingles, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaVocabularioAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.palabra_item, parent, false))
    }

    override fun getItemCount(): Int {
        return palabras.size
    }

    override fun onBindViewHolder(holder: ListaVocabularioAdapter.Pager2ViewHolder, position: Int) {
        var identificador_imagen = context.resources.getIdentifier(palabras[position].imagen, "drawable", "com.example.nextandlearn")
        holder.imagen.setImageResource(identificador_imagen)
        holder.espanol.text = palabras[position].espanol.capitalize()

        //Escondemos el botón en caso de que ya no sea necesario
        if(palabras[position].marcada == true){
            holder.boton.visibility = View.GONE
        }
    }

    //No permitimos que sea reciclable para evitar que al esconder un botón de problemas
    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.setIsRecyclable(false)
    }
    
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            reproductor.language = Locale.UK
        }
    }
}
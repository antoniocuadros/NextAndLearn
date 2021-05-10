package com.example.nextandlearn.fragmentos.adapters

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Palabra
import com.example.nextandlearn.modelo.VocabularioDataBase
import com.example.nextandlearn.modelo.obtenerBaseDatos
import me.relex.circleindicator.CircleIndicator3
import org.w3c.dom.Text
import java.util.*

    /*
        Este adapatador, dada una lista de objetos de tipo Palabra adaptará cada objeto a un item según lo definido en el layout 'palabra\_item' y los añadirá a la vista en forma de slider.

     */
    /*
        Los atributos de esta clase son:
        -> context: Atributo de tipo Context que representa el contexto del fragmento.
        -> modo: Atributo de tipo Int que nos indica si estamos en el apartado mi baraja o estamos en el apartado de vocabulario de una colección.
        -> reproductor: Atributo de tipo TextToSpeech que es utilizado para leer las palabras en inglés.

     */
class ListaVocabularioAdapter(var palabras: MutableList<Palabra>, modo:Int, context: Context, val clickListener: (Palabra, View) -> Unit): RecyclerView.Adapter<ListaVocabularioAdapter.Pager2ViewHolder>(), TextToSpeech.OnInitListener{
    private var context = context
    private var modo = modo
    var reproductor = TextToSpeech(context, this)

        /*
            Esta clase es una clase de tipo inner class, lo que representa que cualquier atributo
            o función de la clase en la que está incluida, en este caso ListaVocabularioAdapter
            puede ser accedido desde dentro. Esta clase es necesaria para poder acceder a cada
            elemento de cada item de la vista y poder definir eventos sobre los mismos.
         */
    inner class Pager2ViewHolder(itemView: View, modo:Int): RecyclerView.ViewHolder(itemView) {
            /*
                Los atributos de esta clase son:
                    -> imagen: Atributo de tipo ImageView, que representa la imagen que acompaña a cada palabra.
                    -> espanol: Atributo de tipo TextView, que representa la palabra tanto en español como en inglés.
                    -> boton: Atributo de tipo ImageButton, que representa el botón de añadir una carta a mi baraja.
                    -> boton_eliminar: Atributo de tipo ImageButton, que representa el botón de eliminar una carta de mi baraja.
                    -> boton_reproducir: Atributo de tipo ImageButton, que representa el botón que se utiliza para escuchar la pronunciación de la palabra en inglés.
                    -> db: Atributo de tipo VocabularioDataBase que nos permite acceder a los datos de la base de datos.
                    -> carta_vacia: Atributo de tipo LinearLayout, que representa la carta cuando no hay elementos, útil para cuando no hay cartas en mi baraja.
                    -> carta_normal: Atributo de tipo LinearLayout, que representa la carta completa cuando hay una o más palabras.
             */
        val imagen = itemView.findViewById<ImageView>(R.id.imagen_palabra)
        val espanol = itemView.findViewById<TextView>(R.id.palabra_espanol)
        val boton = itemView.findViewById<ImageButton>(R.id.boton_anadir2)
        val boton_eliminar = itemView.findViewById<ImageButton>(R.id.boton_eliminar)
        val boton_reproducir = itemView.findViewById<ImageButton>(R.id.boton_reproducir)
        val db:VocabularioDataBase = obtenerBaseDatos(context)
        val carta_vacia = itemView.findViewById<LinearLayout>(R.id.carta_vacia)
        val carta_normal = itemView.findViewById<LinearLayout>(R.id.carta_normal)


        //Para poder hacer click en los elementos
            /*
                El método init se ejecuta cuando se crea un objeto de esta clase y es utilizado
                para definir los listeners de los elementos de cada carta que representa una palabra.
                De esta forma se definen los  listeners de los siguientes elementos:
                    -> itemView: Un clickListener sobre cada item completo, es decir la carta completa, es utilizado para al hacer click voltear la carta y cambiar el idioma.
                    -> boton: Un clickListener sobre el botón de añadir a mi baraja para añadir la palabra a mi baraja.
                    -> boton_eliminar: Un clickListener sobre el botón de eliminar de mi baraja para eliminar la palabra de mi baraja.
                    -> boton_reproducir:  Un clickListener sobre el botón de reproducir para reproducir la palabra en inglés.
             */
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
                boton_eliminar.visibility = View.VISIBLE
            }

            //Listener del botón que elimina de palabras a recordar
            boton_eliminar.setOnClickListener{
                var palabra = palabras[adapterPosition]
                palabra.marcada = false
                db.palabraDao.actualizaPalabra(palabra)
                boton.visibility = View.VISIBLE
                boton_eliminar.visibility = View.GONE
                if(modo == 0){
                    palabras.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, palabras.size)
                    notifyDataSetChanged()
                    var indicador_num = it.rootView.findViewById<CircleIndicator3>(R.id.indicador_slider)
                    indicador_num.createIndicators(palabras.size, adapterPosition+1)

                    if(palabras.size == 0){
                        palabras.add(Palabra("none","none","none","none",true))
                    }
                }

            }



            boton_reproducir.setOnClickListener {
                var palabra = palabras[adapterPosition]
                reproductor.speak(palabra.ingles, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }


    }
    /*
        Se infla la vista de un determinado item a mostrar
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaVocabularioAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.palabra_item, parent, false),modo)
    }

    /*
        Método que devuelve el tamaño de la lista de palabras.
     */
    override fun getItemCount(): Int {
        return palabras.size
    }

    /*
        Se añade el contenido a la imagen y texto de la palbra. Adicionalmente se comprueba
        si no hay ninguna palabra para añadir una carta que indique este caso.
     */
    override fun onBindViewHolder(holder: ListaVocabularioAdapter.Pager2ViewHolder, position: Int) {
        var identificador_imagen = context.resources.getIdentifier(palabras[position].imagen, "drawable", "com.example.nextandlearn")
        holder.imagen.setImageResource(identificador_imagen)
        holder.espanol.text = palabras[position].espanol.capitalize()

        //Escondemos el botón en caso de que ya no sea necesario
        if(palabras[position].marcada == true){
            holder.boton.visibility = View.GONE
            holder.boton_eliminar.visibility = View.VISIBLE
        }

        if(palabras.size == 1 && palabras[0].espanol == "none"){
            holder.carta_normal.visibility = View.GONE
            holder.carta_vacia.visibility = View.VISIBLE
        }
        holder.setIsRecyclable(false)
    }

    /*
        Este método sobrecarga el método onInit de la clase TextToSpeech y es utilizado para definir el lenguaje del reproductor, en este caso inglés.
     */
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            reproductor.language = Locale.UK
        }
    }
}
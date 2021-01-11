package ipvc.estg.trabalhofinal.recycler.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.trabalhofinal.ParagemActivity
import ipvc.estg.trabalhofinal.R
import ipvc.estg.trabalhofinal.recycler.dataclass.Horario

class HorarioAdapter(val list: ArrayList<Horario>, val clickListener: OnHorarioClickListener):RecyclerView.Adapter<HorarioViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.r_horario, parent, false)
        return HorarioViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HorarioViewHolder, position: Int) {
        val horario = list[position]

        holder.init(horario, clickListener)
    }


}

class HorarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val sentido = itemView.findViewById<TextView>(R.id.sentido)
    val empresa = itemView.findViewById<TextView>(R.id.empresa)
    val horarios = itemView.findViewById<TextView>(R.id.horario)

    fun init(horario: Horario, clickListener: OnHorarioClickListener) {

        sentido.text = horario.sentido
        empresa.text = horario.empresa
        horarios.text = horario.horario


        sentido.setOnClickListener {
            clickListener.onHorarioClickListener(horario)
        }
    }
}

interface OnHorarioClickListener{
    fun onHorarioClickListener(horario: Horario)
}

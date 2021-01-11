package ipvc.estg.trabalhofinal.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.trabalhofinal.R
import ipvc.estg.trabalhofinal.recycler.dataclass.Paragem

class ParagemAdapter(val list: ArrayList<Paragem>):RecyclerView.Adapter<ParagemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagemViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.r_paragem, parent, false)
        return ParagemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ParagemViewHolder, position: Int) {
        val paragem = list[position]

        holder.rua.text = paragem.rua
        holder.cidade.text = paragem.cidade
        holder.codpostal.text = paragem.codpostal
    }


}

class ParagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val rua = itemView.findViewById<TextView>(R.id.rua)
    val cidade = itemView.findViewById<TextView>(R.id.cidade)
    val codpostal = itemView.findViewById<TextView>(R.id.codpostal)
}
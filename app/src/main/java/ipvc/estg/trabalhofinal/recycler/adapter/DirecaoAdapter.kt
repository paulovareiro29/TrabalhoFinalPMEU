package ipvc.estg.trabalhofinal.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.trabalhofinal.R
import ipvc.estg.trabalhofinal.recycler.dataclass.Direcao
import ipvc.estg.trabalhofinal.recycler.dataclass.Paragem

class DirecaoAdapter(val list: ArrayList<Direcao>):RecyclerView.Adapter<DirecaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirecaoViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.r_direcao, parent, false)
        return DirecaoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DirecaoViewHolder, position: Int) {
        val direcao = list[position]

        holder.direcao.text = direcao.direcao
    }


}

class DirecaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val direcao = itemView.findViewById<TextView>(R.id.direcao)
}
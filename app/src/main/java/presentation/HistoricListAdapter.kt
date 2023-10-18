package presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pacecalculator.R
import data.HistoricItem

class HistoricListAdapter():RecyclerView.Adapter<HistoricListViewHolder>() {

   private var historyList: List<HistoricItem> = listOf()
    private lateinit var onClickListener: (HistoricItem) -> Unit

    fun submitList(list: List<HistoricItem>){
        this.historyList = list
        notifyDataSetChanged()
    }
    fun setOnDeleteClickListener(onClick: (HistoricItem) -> Unit) {
        this.onClickListener = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_historic, parent, false)
        return HistoricListViewHolder(view)

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: HistoricListViewHolder, position: Int) {
        val item = historyList[position]
        holder.bind(item, onClickListener)
    }
}

class HistoricListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val tvDistancia = view.findViewById<TextView>(R.id.txt_historico_distancia)
    val tvTempo = view.findViewById<TextView>(R.id.txt_historico_tempo)
    val tvRitmo = view.findViewById<TextView>(R.id.txt_historico_ritmo)
    val btnDelete = view.findViewById<ImageButton>(R.id.btn_delete_task)

    fun bind(
        item: HistoricItem,
        onClickListener: (HistoricItem) -> Unit
    ) {
        btnDelete.setOnClickListener {
            onClickListener(item)
        }
        tvTempo.text = "${item.tempo} Min"
        tvDistancia.text = "${item.distancia} Km"
        tvRitmo.text = "Ritmo ${item.ritmo}"
    }

}
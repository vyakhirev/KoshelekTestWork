package ru.vyakhirev.koshelektestwork.presentation.difference.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.model.DiffModel

class DifferenceAdapter(
    private val context: Context,
    private var currencyList: MutableList<DiffModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DifferenceViewHolder(
            LayoutInflater.from(context).inflate(R.layout.difference_item, parent, false)
        )
    }

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is DifferenceViewHolder)
            holder.bind(currencyList[position])
    }

    fun update(data: MutableList<DiffModel>) {
//        val movieDiffUtilCallback = DiffCallback(photos, data)
//        val diffResult = DiffUtil.calculateDiff(movieDiffUtilCallback)
//        diffResult.dispatchUpdatesTo(this)
        currencyList = data
        notifyDataSetChanged()
    }

    fun clear(){
        currencyList.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: DiffModel) {
        currencyList.add(item)
        currencyList.reverse()
        notifyDataSetChanged()
    }
}
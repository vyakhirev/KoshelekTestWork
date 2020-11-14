package ru.vyakhirev.koshelektestwork.presentation.info_ask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.data.CurrencyModel
import ru.vyakhirev.koshelektestwork.data.model.AskModel

class CurrencyAdapter(
    private val context: Context,
    private var currencyList: List<CurrencyModel>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false)
        )
    }

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CurrencyViewHolder)
            holder.bind(currencyList[position])
    }

    fun update(data: List<CurrencyModel>) {
//        val movieDiffUtilCallback = DiffCallback(photos, data)
//        val diffResult = DiffUtil.calculateDiff(movieDiffUtilCallback)
//        diffResult.dispatchUpdatesTo(this)
        currencyList = data
        notifyDataSetChanged()
    }
}
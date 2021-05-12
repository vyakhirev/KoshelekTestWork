package ru.vyakhirev.koshelektestwork.presentation.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.model.CurrencyModel

class CurrencyAdapter(
    private val context: Context,
    private var currencyList: MutableList<CurrencyModel>,
    private val isAsk: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isAsk) return AskViewHolder(

            LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false)
        )
        else
            return BidViewHolder(
                LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false)
            )
    }

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is AskViewHolder)
            holder.bind(currencyList[position])
        if (holder is BidViewHolder)
            holder.bind(currencyList[position])
    }

    fun update(data: MutableList<CurrencyModel>) {
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

    fun addItem(item: CurrencyModel) {
        currencyList.add(item)
        currencyList.reverse()
        notifyDataSetChanged()
    }
}
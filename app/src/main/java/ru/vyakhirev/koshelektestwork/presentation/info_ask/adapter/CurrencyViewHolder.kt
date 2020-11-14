package ru.vyakhirev.koshelektestwork.presentation.info_ask.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item.view.*
import ru.vyakhirev.koshelektestwork.data.CurrencyModel

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

    fun bind(item: CurrencyModel) {
        itemView.amountTV.text = item.amount.format(6).toString()
        itemView.priceTV.text = item.price.format(6).toString()
        var total = item.amount * item.price
        itemView.totalTV.text = total.format(6).toString()
    }
}
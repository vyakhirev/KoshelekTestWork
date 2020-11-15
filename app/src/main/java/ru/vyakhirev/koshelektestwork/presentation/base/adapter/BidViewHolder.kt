package ru.vyakhirev.koshelektestwork.presentation.base.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item.view.*
import ru.vyakhirev.koshelektestwork.data.model.CurrencyModel

class BidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

    fun bind(item: CurrencyModel) {
        itemView.amountTV.text = item.price.format(6).toString()
        itemView.priceTV.text = item.amount.format(6).toString()
        itemView.priceTV.setTextColor(Color.parseColor("#7FFF0000"))
        var total = item.amount * item.price
        itemView.totalTV.text = total.format(6).toString()
    }
}
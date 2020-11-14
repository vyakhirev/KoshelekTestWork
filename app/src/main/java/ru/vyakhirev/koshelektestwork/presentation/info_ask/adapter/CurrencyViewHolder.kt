package ru.vyakhirev.koshelektestwork.presentation.info_ask.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item.view.*
import ru.vyakhirev.koshelektestwork.data.CurrencyModel
import ru.vyakhirev.koshelektestwork.data.model.AskModel

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: CurrencyModel) {
        itemView.amountTV.text = item.amount.toString()
        itemView.priceTV.text = item.price.toString()
        var total=20
        itemView.totalTV.text = total.toString()
    }
}
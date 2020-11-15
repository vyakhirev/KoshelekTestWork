package ru.vyakhirev.koshelektestwork.presentation.difference.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item.view.*
import kotlinx.android.synthetic.main.difference_item.view.*
import ru.vyakhirev.koshelektestwork.data.DiffModel

class DifferenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val Red = "#7FFF0000"
    private val Green = "#FF4CAF50"

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

    fun bind(item: DiffModel) {
        itemView.bidPriceTV.text = item.bidPriceNow.format(6).toString()
        itemView.diffBidTV.text = item.diffBid.format(6).toString()
        if (item.diffBid < 0)
            itemView.priceTV.setTextColor(
                if (item.diffBid < 0)
                    Color.parseColor(Red)
                else
                    Color.parseColor(Green)
            )

        itemView.askPriceTV.text = item.askPriceNow.format(6).toString()
        itemView.diffAskTV.text = item.diffBid.format(6).toString()
        if (item.diffAsk < 0)
            itemView.priceTV.setTextColor(
                if (item.diffBid < 0)
                    Color.parseColor(Red)
                else
                    Color.parseColor(Green)
            )
    }
}
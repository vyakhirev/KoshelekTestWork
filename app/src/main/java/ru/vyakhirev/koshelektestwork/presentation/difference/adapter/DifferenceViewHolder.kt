package ru.vyakhirev.koshelektestwork.presentation.difference.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.difference_item.view.*
import ru.vyakhirev.koshelektestwork.data.model.DiffModel

class DifferenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val red = "#7FFF0000"
    private val green = "#FF4CAF50"

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

    fun bind(item: DiffModel) {
        itemView.bidPriceTV.text = item.bidPriceNow.format(6).toString()
        itemView.diffBidTV.text = item.diffBid.format(6).toString()
        if (item.diffBid < 0)
            itemView.diffBidTV.setTextColor(
                Color.parseColor(red)
            )
        else itemView.diffBidTV.setTextColor(Color.parseColor(green))


        itemView.askPriceTV.text = item.askPriceNow.format(6).toString()
        itemView.diffAskTV.text = item.diffAsk.format(6).toString()
        if (item.diffAsk < 0)
            itemView.diffAskTV.setTextColor(
                Color.parseColor(red)
            )
        else itemView.diffAskTV.setTextColor(Color.parseColor(green))

    }
}
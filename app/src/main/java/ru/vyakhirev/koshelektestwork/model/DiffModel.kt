package ru.vyakhirev.koshelektestwork.model

data class DiffModel(
    var bidPriceNow: Double,
    var bidPricePrevios: Double,
    var diffBid: Double,
    var askPriceNow: Double,
    var askPricePrevios: Double,
    var diffAsk: Double
)
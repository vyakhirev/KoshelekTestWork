package ru.vyakhirev.koshelektestwork.data

data class DiffModel(
    var bidPriceNow:Double,
    var bidPricePrevios:Double,
    var diffBid:Double=bidPriceNow-bidPricePrevios,
    var askPriceNow:Double,
    var askPricePrevios:Double,
    var diffAsk: Double=askPriceNow-askPricePrevios
)
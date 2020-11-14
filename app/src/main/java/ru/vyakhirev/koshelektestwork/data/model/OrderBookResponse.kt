package ru.vyakhirev.koshelektestwork.data.model

//data class OrderBookResponse(
//    var lastUpdatedId: Int,
//    var ask: List<AskModel>,
//    var bid: List<BidsModel>
//)
data class OrderBookResponse(
    var lastUpdateId: Long,
    var bids: List<List<Double>>,
    var asks: List<List<Double>>
)
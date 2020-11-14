package ru.vyakhirev.koshelektestwork.data.model

data class IndividualSymbolBookModel(
    var u: Double,     // order book updateId
    var s: String,     // symbol
    var bPrice: Double, // best bid price
    var BidAmount: Double, // best bid qty
    var aPrice: Double, // best ask price
    var Amount: Double  // best ask qty
)
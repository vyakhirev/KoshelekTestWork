package ru.vyakhirev.koshelektestwork.model

data class CurrencyModel(
    var amount: Double,
    var price: Double,
    var total: Double = amount * price
)
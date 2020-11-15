package ru.vyakhirev.koshelektestwork.data.model

data class CurrencyModel(
    var amount: Double,
    var price: Double,
    var total: Double = amount * price
)
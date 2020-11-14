package ru.vyakhirev.koshelektestwork.data

data class CurrencyModel(
    var amount: Double,
    var price: Double,
    var total: Double = amount * price
)
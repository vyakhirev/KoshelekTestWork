package ru.vyakhirev.koshelektestwork.data.model

import com.google.gson.annotations.SerializedName

data class DepthStreamModel(
    @SerializedName(value = "e")
    var evetType: String,
    @SerializedName(value = "E")
    var eventTime: Double,
    @SerializedName(value = "s")
    var symbol: String,
    @SerializedName(value = "U")
    var firstUpdate: Long,
    @SerializedName(value = "u")
    var finalUpdate: Long,
    @SerializedName(value = "b")
    var bids: List<List<Double>>,
    @SerializedName(value = "a")
    var asks: List<List<Double>>
)
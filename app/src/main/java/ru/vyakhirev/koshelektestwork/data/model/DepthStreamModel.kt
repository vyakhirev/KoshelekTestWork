package ru.vyakhirev.koshelektestwork.data.model

import com.google.gson.annotations.SerializedName

//{
//    "e": "depthUpdate", // Event type
//    "E": 123456789,     // Event time
//    "s": "BNBBTC",      // Symbol
//    "U": 157,           // First update ID in event
//    "u": 160,           // Final update ID in event
//    "b": [              // Bids to be updated
//    [
//        "0.0024",       // Price level to be updated
//        "10"            // Quantity
//    ]
//    ],
//    "a": [              // Asks to be updated
//    [
//        "0.0026",       // Price level to be updated
//        "100"           // Quantity
//    ]
//    ]
//}

data class DepthStreamModel(
    @SerializedName(value = "e")
    var evetType:String,
    @SerializedName(value = "E")
    var eventTime:Double,
    @SerializedName(value = "s")
    var symbol:String,
    @SerializedName(value = "U")
    var firstUpdate:Long,
    @SerializedName(value = "u")
    var finalUpdate:Long,
    @SerializedName(value = "b")
    var bids: List<List<Double>>,
    @SerializedName(value = "a")
    var asks: List<List<Double>>
)
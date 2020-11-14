package ru.vyakhirev.koshelektestwork.data.model

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
    var evetType:String,
    var eventTime:Double,
    var symbol:String,
    var firstUpdate:Int,
    var finalUpdate:Int,
    var bids: List<List<Double>>,
    var asks: List<List<Double>>
)
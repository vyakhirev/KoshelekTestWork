package ru.vyakhirev.koshelektestwork.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vyakhirev.koshelektestwork.data.model.OrderBookResponse

interface ApiBinance {

        @GET("/api/v3/depth")
        fun getOrdersBook(
            @Query("symbol") symbol:String
        ): Single<OrderBookResponse>

    }
package ru.vyakhirev.koshelektestwork.data.remote

import android.util.Log
import com.navin.flintstones.rxwebsocket.RxWebsocket
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okio.ByteString
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vyakhirev.koshelektestwork.data.model.OrderBookResponse


class WsBinance {

    private var websocket: RxWebsocket? = null

    fun onConnect():Single<RxWebsocket.Open> {
        openWebsocket()
        return websocket!!.connect()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { event: RxWebsocket.Open ->
//                    Log.d(
//                        "kan",
//                        event.toString()
//                    )
//                }
//            ) {
////                this.logError(
////                    throwable
//            }
    }

    private fun openWebsocket() {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Authorization", "Bearer ")
                .build()
            chain.proceed(requestBuilder.build())
        })
        websocket = RxWebsocket.Builder()
            .addConverterFactory(WebSocketConverterFactory.create())
            .addReceiveInterceptor { data: String -> "INTERCEPTED:$data" }
            .build(okHttpClientBuilder.build(), "wss://stream.binance.com:9443/ws/bnbbtc@depth")

        websocket!!.eventStream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("kan", it.client().Open().response().toString())
            },
                {
                    Log.d("kan", it.message.toString())
                })
    }
}
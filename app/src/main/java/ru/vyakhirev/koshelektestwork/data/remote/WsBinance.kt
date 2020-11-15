package ru.vyakhirev.koshelektestwork.data.remote

import android.util.Log
import com.navin.flintstones.rxwebsocket.RxWebsocket
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Inject


class WsBinance @Inject constructor(){

    private var websocket: RxWebsocket? = null

    fun onConnect(wsCur:String):Single<RxWebsocket.Open> {
        openWebsocket(wsCur)
        return websocket!!.connect()
    }

    private fun openWebsocket(wsCur:String) {
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
            .addReceiveInterceptor { data: String -> "$data" }
            .build(okHttpClientBuilder.build(), "wss://stream.binance.com:9443/ws/${wsCur}@depth")
    }


    fun onDisconnect() {
        if (websocket != null) {
            websocket!!.disconnect(1000, "Disconnect")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d("WebSocketMy","Disconnect!")
                    },
                 {
                }
                )
        }
    }
}
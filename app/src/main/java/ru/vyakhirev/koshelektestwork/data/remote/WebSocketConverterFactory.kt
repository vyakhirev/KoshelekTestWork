package ru.vyakhirev.koshelektestwork.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.navin.flintstones.rxwebsocket.WebSocketConverter
import java.lang.reflect.Type

class WebSocketConverterFactory private constructor(gson: Gson?) : WebSocketConverter.Factory() {
    private val gson: Gson
    override fun responseBodyConverter(type: Type): GsonResponseConvertor<out Any> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonResponseConvertor(gson, adapter)
    }

    override fun requestBodyConverter(type: Type): WebSocketConverter<*, String> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestConvertor(gson, adapter)
    }

    companion object {
        @JvmOverloads
        fun create(gson: Gson? = Gson()): WebSocketConverterFactory {
            return WebSocketConverterFactory(gson)
        }
    }

    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson = gson
    }
}
package ru.vyakhirev.koshelektestwork.data.remote

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.navin.flintstones.rxwebsocket.WebSocketConverter
import java.io.IOException
import java.nio.charset.Charset

class GsonRequestConvertor<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) :
    WebSocketConverter<T, String> {
    @Throws(IOException::class)
    override fun convert(value: T): String {
        return adapter.toJson(value)
    }

    companion object {
        private val UTF_8 = Charset.forName("UTF-8")
    }
}
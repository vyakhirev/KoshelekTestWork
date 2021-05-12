package ru.vyakhirev.koshelektestwork.data.remote

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.navin.flintstones.rxwebsocket.WebSocketConverter
import java.io.IOException
import java.io.StringReader

class GsonResponseConvertor<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) :
    WebSocketConverter<String?, T> {
    @Throws(IOException::class)
    override fun convert(value: String?): T {
        val jsonReader = gson.newJsonReader(StringReader(value))
        return try {
            adapter.read(jsonReader)
        } finally {
            jsonReader.close()
        }
    }
}

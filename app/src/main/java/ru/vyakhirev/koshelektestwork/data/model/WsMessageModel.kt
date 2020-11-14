package ru.vyakhirev.koshelektestwork.data.model

import com.google.gson.annotations.SerializedName

class WsMessageModel(
    @field:SerializedName("id") private val id: Int, @field:SerializedName(
        "message"
    ) private val message: String
) {
    fun id(): Int {
        return id
    }

    fun message(): String {
        return message
    }
}

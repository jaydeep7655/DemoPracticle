package com.example.demopracticle.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelCheckBox(
    @SerializedName("count")
    val count: Int,
    @SerializedName("value")
    var value: Boolean
):Serializable
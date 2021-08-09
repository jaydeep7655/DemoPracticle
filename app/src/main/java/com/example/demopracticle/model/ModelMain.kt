package com.example.demopracticle.model

import java.io.Serializable
import java.text.FieldPosition

data class ModelMain(
    val position: Int,
    val list:List<ModelCheckBox>
):Serializable

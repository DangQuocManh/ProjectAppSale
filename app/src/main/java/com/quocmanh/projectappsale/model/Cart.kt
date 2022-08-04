package com.quocmanh.projectappsale.model

import com.google.gson.annotations.SerializedName

class Cart  {
    @SerializedName("result")
    val result : MutableList<ModelCart> = mutableListOf()
}
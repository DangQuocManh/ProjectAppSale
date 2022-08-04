package com.quocmanh.projectappsale.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserModel{
    @SerializedName("success")
    val success : Boolean = true
    @SerializedName("result")
    val result : MutableList<User> = mutableListOf()
}
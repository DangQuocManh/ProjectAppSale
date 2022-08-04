package com.quocmanh.projectappsale.model

import com.google.gson.annotations.SerializedName

class ManageUserModel {
    @SerializedName("success")
    val success : Boolean = true
    @SerializedName("result")
    val result : MutableList<ManageUser> = mutableListOf()
}
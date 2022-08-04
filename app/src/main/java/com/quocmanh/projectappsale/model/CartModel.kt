package com.quocmanh.projectappsale.model

import java.io.Serializable

data class CartModel(val id : Int, val name : String, val image : String, var price : String, var count : Int) : Serializable{

}
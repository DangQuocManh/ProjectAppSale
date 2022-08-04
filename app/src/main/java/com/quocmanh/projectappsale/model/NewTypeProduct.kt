package com.quocmanh.projectappsale.model

import java.io.Serializable

data class NewTypeProduct(var id : Int, var name : String, var price : String, var image : String, var description : String,
                          var type : Int)  : Serializable{
}
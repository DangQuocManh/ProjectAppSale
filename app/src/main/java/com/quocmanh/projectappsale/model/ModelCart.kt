package com.quocmanh.projectappsale.model

data class ModelCart (val id : Int,
                      val name : String,
                      val image : String,
                      var price : String,
                      var count : Int,
                      val iduser : Int){
}
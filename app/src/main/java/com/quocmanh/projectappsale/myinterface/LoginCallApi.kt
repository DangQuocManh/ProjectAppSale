package com.quocmanh.projectappsale.myinterface

import com.quocmanh.projectappsale.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*
import java.io.Serializable

interface LoginCallApi : Serializable{
    @POST("/Appsale/insert_user.php")
    @FormUrlEncoded
    fun login(
        //@Body body : UserModel
        @Field("username") username:String,
        @Field("email") email:String,
        @Field("mobile") mobile:String,
        @Field("password") password:String,
        @Field("type") type : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/login.php")
    @FormUrlEncoded
    fun login1(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Observable<UserModel>

    @POST("/Appsale/delete_cart.php")
    @FormUrlEncoded
    fun deleteCart(
        @Field("name") name : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/update_add_product_cart.php")
    @FormUrlEncoded
    fun updateCountProductCart(
        @Field("name") name : String,
        @Field("price") price : String,
        @Field("count") count : Int
    ) : Observable<SuccessModel>

    @POST("/Appsale/get_cart.php")
    @FormUrlEncoded
    fun getCart(
        @Field("iduser") iduser : Int
    ) : Observable<Cart>

    @POST("/Appsale/insert_order.php")
    @FormUrlEncoded
    fun insertOrder(
        @Field("iduser") iduser : Int,
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("mobile") mobile : String,
        @Field("money") money : String,
        @Field("date") date : String,
        @Field("detail_order") detailOrder : String,
        @Field("address") address : String
    ) : Observable<SuccessModel>

    @GET("/Appsale/get_mn_product.php")
    fun getManageProduct(

    ) : Observable<ManageProductModel>

    @GET("/Appsale/get_mn_user.php")
    fun getManageUser(

    ) : Observable<ManageUserModel>
    @POST("/Appsale/delete_product.php")
    @FormUrlEncoded
    fun deleteProduct(
        @Field("name") name : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/update_product.php")
    @FormUrlEncoded
    fun updateProduct(
        @Field("id") id : Int,
        @Field("name") name : String,
        //@Field("image") image : String,
        @Field("price") price : String,
        @Field("description") description : String,
        @Field("type") type : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/inser_product.php")
    @FormUrlEncoded
    fun insertProduct(
        @Field("name") name : String,
        @Field("price") price : String,
        @Field("description") description: String,
        @Field("type") type : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/delete_user.php")
    @FormUrlEncoded
    fun deleteUser(
        @Field("username") username : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/update_user.php")
    @FormUrlEncoded
    fun updateUser(
        @Field("id") id : Int,
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("mobile") mobile : String,
        @Field("password") password : String,
        @Field("type") type : String
    ) : Observable<SuccessModel>

    @POST("/Appsale/insert_user_admin.php")
    @FormUrlEncoded
    fun insertUserAdmin(
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("mobile") mobile : String,
        @Field("password") password : String,
        @Field("type") type : String
    ) : Observable<SuccessModel>
}
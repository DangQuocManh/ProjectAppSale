package com.quocmanh.projectappsale.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.adapter.CartAdapter
import com.quocmanh.projectappsale.adapter.TypeProductAdapter
import com.quocmanh.projectappsale.model.CartModel
import com.quocmanh.projectappsale.model.ModelCart
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.model.TypeProduct
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat

class CartActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imgBack : ImageButton
    private lateinit var recyclerViewCart : RecyclerView
    private lateinit var txtTotal : TextView
    private lateinit var btnCheckout : Button
    private lateinit var listCart : MutableList<CartModel>
    //val urlGetCart : String = "http://192.168.0.104:8080/Appsale/get_cart.php"
    private lateinit var shared : SharedPreferences
    private var totalMoney : Long? = null
    private lateinit var loginCallApi: LoginCallApi
    private lateinit var idUser : String
    private lateinit var cartList : MutableList<ModelCart>
    private lateinit var detailOrder : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
        getCart()
    }
    private fun getCart(){
        idUser = shared.getString("id", "").toString()
        Log.d("dddddd", idUser)
        val idUserInt = idUser.toInt()
        loginCallApi.getCart(
            idUserInt
        )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("result.size", it.result.size.toString())
                for(i in 0..(it.result.size - 1)){
                    val id : Int = it.result[i].id
                    val name : String = it.result[i].name
                    val image : String = it.result[i].image
                    val price : String = it.result[i].price
                    val count : Int = it.result[i].count
                    val idUser : Int = it.result[i].iduser
                    cartList.add(ModelCart(id, name, image, price, count, idUser))
                }
                    //getData()
                    val adapter = CartAdapter(this@CartActivity, cartList)
                    recyclerViewCart.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
                    recyclerViewCart.adapter = adapter
                    getTotalMoney(cartList)
                },
                {

                })
    }

    fun getTotalMoney(listCart : MutableList<ModelCart>){
        totalMoney = 0
        if(listCart == null || listCart.isEmpty() || listCart.size == 0){
            Toast.makeText(this, "Chưa có sản phẩm trong giỏ hàng!", Toast.LENGTH_LONG).show()
        }else {
            for (i in 0..(listCart.size-1)) {
                totalMoney = totalMoney!! + listCart[i].price.toInt().toLong()
            }
        }
        val decimalFormat = DecimalFormat("###,###,###")
        txtTotal.setText(decimalFormat.format(totalMoney).toString() + " Đ")
    }

    fun init(){
        imgBack = findViewById(R.id.imb_back)
        recyclerViewCart = findViewById((R.id.recyclerview_cart))
        txtTotal = findViewById(R.id.txt_total)
        btnCheckout = findViewById(R.id.btn_checkout)
        listCart = mutableListOf()
        imgBack.setOnClickListener(this)
        btnCheckout.setOnClickListener(this)
        shared = applicationContext.getSharedPreferences("MyPreferences", MODE_PRIVATE)
        loginCallApi = RetrofitFactor.createRetrofit()
        cartList = mutableListOf()
        detailOrder = ""
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.imb_back -> {
                onBackPressed()
            }
            R.id.btn_checkout -> {
                val intent = Intent()
                intent.setClass(this, PayActivity::class.java)
                intent.putExtra("money", totalMoney)
                for(i in 0..(cartList.size - 1)){
                    detailOrder += cartList[i].name + " - Số lượng: " + cartList[i].count + "\n"

                }
                Log.d("testddddd", detailOrder)
                intent.putExtra("detail", detailOrder)
                detailOrder = ""
                startActivity(intent)
            }
        }
    }

}
package com.quocmanh.projectappsale.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SharedMemory
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.nex3z.notificationbadge.NotificationBadge
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.CartModel
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.utils.Utils
import java.text.DecimalFormat

class DetailphoneActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imageDetailPhone : ImageView
    private lateinit var nameDetailPhone : TextView
    private lateinit var priceDetailPhone : TextView
    private lateinit var spinner : Spinner
    private lateinit var description : TextView
    private lateinit var btnAddCart : Button
    private lateinit var btnBack : ImageButton
    private lateinit var imvCart : ImageView
    private lateinit var badge : NotificationBadge
    private lateinit var shared : SharedPreferences
    private lateinit var userId : String
    var main : MainActivity = MainActivity()
    val urlUpdateAndInsert : String = "http://192.168.0.4:8080/Appsale/update_and_insert_cart.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_phone)
        init()
        getData()
        addCart()
    }

    private fun addCart() {
        userId = shared.getString("id", "").toString()
        Log.d("ddddddd", userId)
        btnAddCart.setOnClickListener(View.OnClickListener {
            controlCart()
            add(urlUpdateAndInsert)
        })
    }

    private fun add(url : String) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener<String> {
                response -> if(response.trim().equals("true")){
                    Toast.makeText(this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_LONG).show()
        }else if(response.trim().equals("false")){
            Toast.makeText(this, "Cập nhật giỏ hàng thành công!", Toast.LENGTH_LONG).show()
        }
            },
            //Loi server hoac loi link
                Response.ErrorListener {
                        error ->
                    val toast = Toast.makeText(this, error.toString(), Toast.LENGTH_LONG)
                    toast.show()
                    Log.d("AAA", "Loi\n " + error.toString())
                }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    //Change with your post params
                    params["name"] = main.utils.listCart[0].name
                    params["image"] = main.utils.listCart[0].image
                    params["price"] = main.utils.listCart[0].price
                    params["count"] = main.utils.listCart[0].count.toString()
                    params["iduser"] = userId
                    return params
                }
            }
        requestQueue.add(stringRequest)
    }

    private fun controlCart() {
        var count : Int
        var price : Long
        var id : Int
        var name : String
        var image : String

        val bundle : Bundle = intent.extras!!
        if(bundle == null){
            Toast.makeText(this, "Khong du lieu", Toast.LENGTH_LONG).show()
        }
        var imagePhone = bundle.get("object") as NewTypeProduct
        count = spinner.selectedItem.toString().toInt()
        price = imagePhone.price.toLong() * count
        name = imagePhone.name
        image = imagePhone.image
        id = imagePhone.id
        main.utils.listCart!!.add(CartModel(id, name, image, price.toString(), count))

    }

    //Nhận dữ liệu từ SmartphoneActivity
    private fun getData(){
        val bundle : Bundle = intent.extras!!
        if(bundle == null){
            Toast.makeText(this, "Khong du lieu", Toast.LENGTH_LONG).show()
        }
        var imagePhone = bundle.get("object") as NewTypeProduct
        Glide.with(this)
            .load(imagePhone.image)
            .into(imageDetailPhone)
        nameDetailPhone.setText(imagePhone.name)
        val decimalFormat : DecimalFormat = DecimalFormat("###,###,###")
        priceDetailPhone.setText("Giá " + decimalFormat.format(imagePhone.price.toDouble()) + "Đ")
        description.setText(imagePhone.description)
    }
    private fun init(){
        imageDetailPhone = findViewById(R.id.detail_image_phone)
        nameDetailPhone = findViewById(R.id.detail_name)
        priceDetailPhone = findViewById(R.id.detail_price)
        spinner = findViewById(R.id.detail_spinner)
        description = findViewById(R.id.detail_description)
        btnAddCart = findViewById(R.id.btn_addcart)
        btnBack = findViewById(R.id.btn_back)
        imvCart = findViewById(R.id.imv_cart)
        badge = findViewById(R.id.badge)
        btnBack.setOnClickListener(this)
        imvCart.setOnClickListener(this)

        var count = arrayOf(1,2,3,4,5,6,7,8,9,10)
        val adapterSpin : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, count)
        spinner.setAdapter(adapterSpin)

        if(main.utils.listCart.size != null){
            badge.setText(main.utils.listCart.size.toString())
        }
        shared = applicationContext.getSharedPreferences("MyPreferences", MODE_PRIVATE)
    }
////Bắt sự kiện
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_back -> {
                onBackPressed()
            }
            //Nhấn vào image giỏ hàng chuyển sang activity cart
            R.id.imv_cart -> {
                val intent = Intent()
                intent.setClass(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
    }
}



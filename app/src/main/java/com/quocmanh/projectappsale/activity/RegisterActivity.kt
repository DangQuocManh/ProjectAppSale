package com.quocmanh.projectappsale.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.UserModel
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterActivity : AppCompatActivity(){
    private lateinit var username : EditText
    private lateinit var email : EditText
    private lateinit var mobile : EditText
    private lateinit var password : EditText
    private lateinit var passwordAgain : EditText
    private lateinit var register : Button
    private lateinit var loginCallApi : LoginCallApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
        register()
    }
    private fun register(){
        register.setOnClickListener {
            login()
        }
    }
    private fun init(){
        username = findViewById(R.id.et_username1)
        email = findViewById(R.id.et_email)
        mobile = findViewById(R.id.et_mobile)
        password = findViewById(R.id.et_password)
        passwordAgain = findViewById(R.id.et_password_again)
        register = findViewById(R.id.btn_register)
        loginCallApi = RetrofitFactor.createRetrofit()
    }
    private fun login(){
        var username = username.text.toString().trim()
        var email = email.text.toString().trim()
        var mobile = mobile.text.toString().trim()
        var password = password.text.toString().trim()
        if(username.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập username!", Toast.LENGTH_LONG).show()
        }else if(email.isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập email!", Toast.LENGTH_LONG).show()
        }else if(mobile.isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập mobile!", Toast.LENGTH_LONG).show()
        }else if(!email.contains("@gmail.com")){
            Toast.makeText(this, "Không đúng định dạng email!", Toast.LENGTH_LONG).show()
        }else if(mobile.length > 11 || mobile.length < 10) {
            Toast.makeText(this, "Không đúng định dạng số điện thoại!", Toast.LENGTH_LONG).show()
        }else
//            if(!mobile.startsWith("083") || !mobile.startsWith("084") || !mobile.startsWith("085") ||
//            !mobile.startsWith("081") || !mobile.startsWith("082") || !mobile.startsWith("032") ||
//            !mobile.startsWith("033") || !mobile.startsWith("034") || !mobile.startsWith("035")||
//            !mobile.startsWith("036") || !mobile.startsWith("037") || !mobile.startsWith("038") ||
//            !mobile.startsWith("039") || !mobile.startsWith("070") || !mobile.startsWith("076") ||
//            !mobile.startsWith("077") || !mobile.startsWith("078") || !mobile.startsWith("079") ||
//            !mobile.startsWith("056") || !mobile.startsWith("058") || !mobile.startsWith("059") ) {
//            Toast.makeText(this, "Không đúng định dạng số điện thoại!", Toast.LENGTH_LONG).show()
//        }else
            if(password.length < 6) {
            Toast.makeText(this, "Mật khẩu phải dài hơn 6 kí tự!", Toast.LENGTH_LONG).show()
        }else if(password.isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập password!", Toast.LENGTH_LONG).show()
        }else if(passwordAgain.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Bạn chưa xác minh password!", Toast.LENGTH_LONG).show()
        }else if(passwordAgain.text.toString().trim() != password){
            Toast.makeText(this, "Password xác minh sai!", Toast.LENGTH_LONG).show()
        }
        else {
            val type : String = "client"
            loginCallApi.login(
                username, email, mobile, password, type
            )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(it.isSuccess == true){
                            Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_LONG).show()
                            val intent = Intent()
                            intent.setClass(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    },
                    {

                    }
                )
           }
        }
    }

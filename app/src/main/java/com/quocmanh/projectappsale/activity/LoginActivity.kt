package com.quocmanh.projectappsale.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.activity.admin.AdminActivity
import com.quocmanh.projectappsale.model.SuccessModel
import com.quocmanh.projectappsale.model.User
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginActivity : AppCompatActivity(){
    private lateinit var register : TextView
    private lateinit var userLogin : EditText
    private lateinit var passLogin : EditText
    private lateinit var login : Button
    private lateinit var loginCallApi: LoginCallApi
    private lateinit var user : MutableList<User>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        register()
        loginButton()
    }
    private fun loginButton(){
        login.setOnClickListener {
            login()
        }
    }
    private fun login(){
        var userLogin = userLogin.text.toString().trim()
        var passLogin = passLogin.text.toString().trim()
        loginCallApi.login1(
            userLogin, passLogin
        )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                   if(it.success == true && it.result[0].type == "client"){
                       Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show()
                       editor.putString("id", it.result[0].id.toString())
                       editor.putString("username", it.result[0].username)
                       editor.putString("email", it.result[0].email)
                       editor.putString("mobile", it.result[0].mobile)
                       editor.putString("password", it.result[0].password)
                       editor.commit()
                       val intent = Intent()
                       intent.setClass(this, MainActivity::class.java)
                       startActivity(intent)
                   }else if(it.success == true && it.result[0].type == "admin") {
                       Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show()
                       val intent = Intent()
                       intent.setClass(this, AdminActivity::class.java)
                       startActivity(intent)
                   }else{
                       Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_LONG).show()
                   }
                },
                {

                })
    }
    private fun init(){
        register = findViewById(R.id.txt_register)
        userLogin = findViewById(R.id.et_username_login)
        passLogin = findViewById(R.id.et_password_login)
        login = findViewById(R.id.btn_login)
        loginCallApi = RetrofitFactor.createRetrofit()
        user = mutableListOf()
        sharedPreferences = applicationContext.getSharedPreferences("MyPreferences", MODE_PRIVATE)
        editor = sharedPreferences.edit()

    }
    private fun register(){
        register.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}
package com.quocmanh.projectappsale.activity

import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.DecimalFormat
import android.icu.text.StringPrepParseException
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Delayed
import kotlin.properties.Delegates


class PayActivity : AppCompatActivity() {
    private lateinit var shared : SharedPreferences
    private lateinit var userName : EditText
    private lateinit var email : EditText
    private lateinit var phone : EditText
    private lateinit var money : TextView
    private lateinit var pay : Button
    private lateinit var idUser : String
    private lateinit var username : String
    private lateinit var emailUser : String
    private lateinit var phoneUser : String
    private lateinit var dateNow : String
    private lateinit var detailOrder : String
    private lateinit var detailPay :EditText
    private lateinit var loginCallApi: LoginCallApi
    private var moneyTotal : Long = 0
    private lateinit var addressPay : EditText
    private lateinit var address : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        init()
        getInfor()
        order()
    }

    private fun getInfor(){
        idUser = shared.getString("id", "").toString()
        username = shared.getString("username", "").toString()
        emailUser = shared.getString("email", "").toString()
        phoneUser = shared.getString("mobile", "").toString()
        val intent : Intent = getIntent()
        moneyTotal = intent.getLongExtra("money", 0)
        val format : NumberFormat = NumberFormat.getInstance()
        detailOrder = intent.getStringExtra("detail").toString()
        money.setText(format.format(moneyTotal) + " Đ")
        userName.setText(username)
        email.setText(emailUser)
        phone.setText(phoneUser)
        detailPay.setText(detailOrder)
    }
    private fun order(){
        val simpleDateFormat : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        dateNow = simpleDateFormat.format(Date())
        pay.setOnClickListener {
            address = addressPay.text.toString().trim()
            val factory = LayoutInflater.from(this)
            val dialogAlert : View = factory.inflate(R.layout.alert_dialog, null)
            val dialog : AlertDialog = AlertDialog.Builder(this).create()
            dialog.setView(dialogAlert)
            dialogAlert.findViewById<Button>(R.id.btn_yes).setOnClickListener {
                loginCallApi.insertOrder(
                    idUser.toInt(), username, emailUser, phoneUser, moneyTotal.toString(), dateNow, detailOrder, address
                )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                        if(it.isSuccess == true){
                             Handler().postDelayed({ dialog.dismiss() }
                                , 1500)
                        }
                        },
                        {

                        })

            }
            dialogAlert.findViewById<Button>(R.id.btn_exit).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun init(){
        shared = applicationContext.getSharedPreferences("MyPreferences", MODE_PRIVATE)
        userName = findViewById(R.id.et_username_pay)
        email = findViewById(R.id.et_email_pay)
        phone = findViewById(R.id.et_phone_pay)
        money = findViewById(R.id.txt_money)
        pay = findViewById(R.id.btn_pay)
        detailPay = findViewById(R.id.et_detail_pay)
        loginCallApi = RetrofitFactor.createRetrofit()
        addressPay = findViewById(R.id.et_address_pay)
    }
}
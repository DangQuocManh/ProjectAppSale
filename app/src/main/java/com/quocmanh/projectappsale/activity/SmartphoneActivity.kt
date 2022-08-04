package com.quocmanh.projectappsale.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.adapter.NewTypeProductAdapter
import com.quocmanh.projectappsale.adapter.PhoneAdapter
import com.quocmanh.projectappsale.model.ManageUser
import com.quocmanh.projectappsale.model.NewTypeProduct
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.quocmanh.projectappsale.myinterface.InterfacePhone

class SmartphoneActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var imgBack : ImageButton
    val urlGetPhone : String = "http://192.168.0.4:8080/Appsale/get_type_phone.php"
    private lateinit var recyclerViewPhone : RecyclerView
    private var listPhone : MutableList<NewTypeProduct> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smartphone)
        init()
        GetDataPhone().execute(urlGetPhone)
    }
    //Nhận dữ liệu từ database
    inner class GetDataPhone(): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
            var jsonArray : JSONArray = JSONArray(result)
            var id: Int? = null
            var name: String = ""
            var price : String = ""
            var image: String = ""
            var description : String = ""
            var type : Int? = null

            for(typeNewProduct in 0..jsonArray.length()-1){
                var objectType: JSONObject = jsonArray.getJSONObject(typeNewProduct)
                id = objectType.getInt("id")
                name = objectType.getString("name")
                price = objectType.getString("price")
                image = objectType.getString("image")
                description = objectType.getString("description")
                type = objectType.getInt("type")
                listPhone.add(NewTypeProduct(id, name, price, image, description, type))
            }
            val adapter = PhoneAdapter(listPhone, object : InterfacePhone {
                override fun onClickItemPhone(productPhone: NewTypeProduct) {
                    onClickGoToDetailPhone(productPhone)
                }

                override fun onClickItemUser(user: ManageUser) {
                    TODO("Not yet implemented")
                }
            })
            //Đưa dữ liệu lên recyclerview
            //recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerViewPhone.layoutManager = LinearLayoutManager(this@SmartphoneActivity, LinearLayoutManager.VERTICAL, false)
            recyclerViewPhone.adapter = adapter
        }
    }
    private fun onClickGoToDetailPhone(productPhone: NewTypeProduct){
        val intent = Intent()
        intent.setClass(this, DetailphoneActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("object", productPhone)
        intent.putExtras(bundle)
        startActivity(intent)
    }
    private fun getContentURL(url: String?) : String{
        var content: StringBuilder = StringBuilder()
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try {
            do {
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line != null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA", e.toString())
        }
        return content.toString()
    }
    private fun init(){
        recyclerViewPhone = findViewById(R.id.rc_phone)
        imgBack = findViewById(R.id.imb_back)
        imgBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.imb_back -> {
                onBackPressed()
            }
        }
    }
}
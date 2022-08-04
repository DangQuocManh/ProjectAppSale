package com.quocmanh.projectappsale.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.adapter.NewTypeProductAdapter
import com.quocmanh.projectappsale.adapter.TypeProductAdapter
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.model.TypeProduct
import com.quocmanh.projectappsale.utils.Utils
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.android.schedulers.AndroidSchedulers.*
//import io.reactivex.functions.Consumer
//import io.reactivex.rxjava3.core.Observable
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var trangChinh : TextView
    private lateinit var flipper: ViewFlipper
    private lateinit var recyclerView: RecyclerView
    private lateinit var navigation: NavigationView
    private lateinit var listView : ListView
    private lateinit var drawLayout : DrawerLayout
    private lateinit var btnMain : ImageButton
    private lateinit var imbCart : ImageButton
    private var listTypeProduct : MutableList<TypeProduct> = mutableListOf()
    private var listNewTypeProduct : MutableList<NewTypeProduct> = mutableListOf()
    var utils = Utils()
    private lateinit var listAdapter : TypeProductAdapter
    val urlGetData : String = "http://192.168.0.4:8080/Appsale/get_typte_product.php"
    val urlGetDataNewProduct : String = "http://192.168.0.4:8080/Appsale/get_type_new_product.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        if(utils.listCart == null){
            utils.listCart = mutableListOf()
        }

        openDrawerLayout()
        if(isConnect(this)){
            //Chay quang cao
            actionViewFlipper()
            //Nhan du lieu tu database va hien du lieu len listview man hinh chinh
            GetData().execute(urlGetData)
            GetDataNewProduct().execute(urlGetDataNewProduct)
            getEventListView()
        }else{
            Toast.makeText(applicationContext, "Khong co internet", Toast.LENGTH_LONG).show()
        }
    }

    private fun getEventListView() {
        listView.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            when(i){
               0 -> {
                   val intent = Intent()
                   intent.setClass(this, MainActivity::class.java)
                   startActivity(intent)
               }
                1 -> {
                    val intent1 = Intent()
                    intent1.setClass(this, SmartphoneActivity::class.java)
                    startActivity(intent1)
                }
                2 -> {
                    val intent2 = Intent()
                    intent2.setClass(this, LaptopActivity::class.java)
                    startActivity(intent2)
                }
            }
        })
    }

    inner class GetData(): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var jsonArray : JSONArray = JSONArray(result)
            var id: Int? = null
            var name: String = ""
            var image: String = ""

            for(typeProduct in 0..jsonArray.length()-1){
                    var objectType: JSONObject = jsonArray.getJSONObject(typeProduct)
                    id = objectType.getInt("id")
                    name = objectType.getString("name_product")
                    image = objectType.getString("image_product")
                listTypeProduct.add(TypeProduct(id, name, image))
                }
            listAdapter = TypeProductAdapter(applicationContext, listTypeProduct)
            listView.adapter = listAdapter
           }
    }
    inner class GetDataNewProduct(): AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
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
                listNewTypeProduct.add(NewTypeProduct(id, name, price, image, description, type))
            }
            val adapter = NewTypeProductAdapter(listNewTypeProduct)
            //recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
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

    private fun openDrawerLayout(){
        trangChinh.setOnClickListener {
            drawLayout.openDrawer(GravityCompat.START)
        }
        btnMain.setOnClickListener{
            drawLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun actionViewFlipper(){
        val listAd : MutableList<String> = mutableListOf();
        listAd.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg")
        listAd.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png")
        listAd.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png")
        for(i in 0..listAd.size-1){
            val imageView = ImageView(getApplicationContext())
            Glide
                .with(getApplicationContext())
                .load(listAd.get(i))
                .into(imageView)
            imageView.setScaleType(ImageView.ScaleType.FIT_XY)
            flipper.addView(imageView)
        }
        flipper.setFlipInterval(3000)
        flipper.setAutoStart(true)
        val adIn : Animation = AnimationUtils.loadAnimation(getApplicationContext(),
            R.anim.ad_in_right
        )
        val adOut : Animation = AnimationUtils.loadAnimation(getApplicationContext(),
            R.anim.ad_out_right
        )
        flipper.setAnimation(adIn)
        flipper.setAnimation(adOut)
    }

    private fun init(){
        trangChinh = findViewById(R.id.trangchinh)
        flipper = findViewById(R.id.vf_viewflipper)
        recyclerView = findViewById(R.id.rc_recyclerview)
        navigation = findViewById(R.id.nv_navigaview)
        listView = findViewById(R.id.lv_listview)
        drawLayout = findViewById(R.id.drawerlayout)
        btnMain = findViewById(R.id.btn_main)
        imbCart = findViewById(R.id.imb_cart_main)
        imbCart.setOnClickListener(this)
        //Khoi tao list
        listTypeProduct = mutableListOf()
    }

    private fun isConnect(context : Context) : Boolean{
        val connectivityManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi : NetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!
        val mobile : NetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!
        if((wifi != null && wifi.isConnected) || (mobile != null && mobile.isConnected)){
            return true
        }else{
            return false
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.imb_cart_main -> {
                val intent = Intent()
                intent.setClass(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

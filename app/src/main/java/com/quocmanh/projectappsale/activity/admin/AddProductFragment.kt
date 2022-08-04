package com.quocmanh.projectappsale.activity.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : Fragment(), View.OnClickListener {
    private lateinit var imageProduct : ImageView
    private lateinit var nameProduct : EditText
    private lateinit var priceProduct : EditText
    private lateinit var descripProduct : EditText
    private lateinit var typeProduct : EditText
    private lateinit var addProduct : Button
    private lateinit var loginCallApi: LoginCallApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add_product, container, false)
        init(view)
        return view
    }
    private fun init(view : View){
        imageProduct = view.findViewById(R.id.imv_image_product)
        nameProduct = view.findViewById(R.id.edt_name_product)
        priceProduct = view.findViewById(R.id.edt_price_product)
        descripProduct = view.findViewById(R.id.edt_description_product)
        typeProduct = view.findViewById(R.id.edt_type_product)
        addProduct = view.findViewById(R.id.btn_add_product)
        addProduct.setOnClickListener(this)
        loginCallApi = RetrofitFactor.createRetrofit()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_add_product -> {
                val name = nameProduct.text.toString().trim()
                val price = priceProduct.text.toString().trim()
                val description = descripProduct.text.toString().trim()
                val type = typeProduct.text.toString().trim()
                loginCallApi.insertProduct(name, price, description, type)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(context, "Them thanh cong!", Toast.LENGTH_LONG).show()
                    },
                        {

                        })
            }
        }
    }

}
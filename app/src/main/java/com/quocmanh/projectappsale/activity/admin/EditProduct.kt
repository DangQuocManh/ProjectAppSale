package com.quocmanh.projectappsale.activity.admin

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.myinterface.InterfacePhone
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProduct.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProduct : Fragment(), View.OnClickListener {
    private lateinit var imageProduct : ImageView
    private lateinit var nameProduct : EditText
    private lateinit var priceProduct : EditText
    private lateinit var descripProduct : EditText
    private lateinit var typeProduct : EditText
    private lateinit var btnEdit : Button
    private var idProduct : Int? = null
    private lateinit var loginCallApi: LoginCallApi
    fun EditProduct(){

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_edit_product, container, false)
        init(view)
        getData(view)
        return view
    }
    private fun init(view : View){
        imageProduct = view.findViewById(R.id.imv_image_edit_product)
        nameProduct = view.findViewById(R.id.edit_name_edit)
        priceProduct = view.findViewById(R.id.edit_price_edit)
        descripProduct = view.findViewById(R.id.edt_description_edit)
        typeProduct = view.findViewById(R.id.edit_type_edit)
        btnEdit = view.findViewById(R.id.btn_edit)
        btnEdit.setOnClickListener(this)
        loginCallApi = RetrofitFactor.createRetrofit()

    }
    private fun getData(view : View){
        idProduct = arguments?.getString("id")!!.toInt()
        val image : String = arguments?.getString("image")!!
        val name :String = arguments?.getString("name")!!
        val price : String = arguments?.getString("price")!!
        val description : String = arguments?.getString("descryption")!!
        val type : String = arguments?.getString("type")!!
        val decimalFormat : DecimalFormat = DecimalFormat("###,###,###")
        Glide.with(view)
            .load(image)
            .into(imageProduct)
        nameProduct.setText(name)
        //priceProduct.setText(decimalFormat.format(price.toLong()).toString())
        priceProduct.setText(price)
        descripProduct.setText(description)
        typeProduct.setText(type)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_edit -> {
                val idP = idProduct
                val name : String = nameProduct.text.toString().trim()
                val price : String = priceProduct.text.toString().trim()
                val description : String = descripProduct.text.toString().trim()
                val type : String = typeProduct.text.toString().trim()
                loginCallApi.updateProduct(
                    idP!!, name, price, description, type
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                               if(it.isSuccess == true){
                                   val builder = AlertDialog.Builder(requireContext())
                                   builder.setTitle("Xác nhận")
                                   builder.setMessage("Cập nhập sản phẩm!")
                                   builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                                       dialogInterface.cancel()
                                   })
                               }else{
                                   Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show()
                               }
                    },
                        {

                        })
            }
        }
    }

}
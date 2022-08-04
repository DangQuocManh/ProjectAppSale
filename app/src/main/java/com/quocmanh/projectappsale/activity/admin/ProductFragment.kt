package com.quocmanh.projectappsale.activity.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.adapter.MnproductAdapter
import com.quocmanh.projectappsale.model.ManageUser
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.myinterface.InterfacePhone
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
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment(), View.OnClickListener {
    private lateinit var loginCallApi: LoginCallApi
    private lateinit var recycler : RecyclerView
    private lateinit var listProduct : MutableList<NewTypeProduct>
    private lateinit var imbBack : ImageButton
    private lateinit var imbAdd : ImageButton
    private lateinit var fragmentAdd: AddProductFragment
    private lateinit var editFragment : EditProduct
    private lateinit var iSendData : ISendDataListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_product, container, false)
        init(view)
        getProduct(view)
        return view
    }
    //Tao interface de chuyen du lieu
    interface ISendDataListener{
        //Ham chuyen du lieu
        fun sendData(product: NewTypeProduct)
    }
    //Khoi tao doi tuong ISendDataListener tron onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
        iSendData = activity as ISendDataListener
    }
    private fun init(view : View){
        loginCallApi = RetrofitFactor.createRetrofit()
        recycler = view.findViewById(R.id.rcv_product)
        listProduct = mutableListOf()
        recycler.setHasFixedSize(true)
        imbBack = view.findViewById(R.id.imb_back)
        imbAdd = view.findViewById(R.id.imb_add_product)
        imbBack.setOnClickListener(this)
        imbAdd.setOnClickListener(this)
        fragmentAdd = AddProductFragment()
        editFragment = EditProduct()
    }
    private fun getProduct(view : View){
        loginCallApi.getManageProduct()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                for(i in 0..(it.result.size-1)){
                    val id : Int = it.result[i].id
                    val name : String = it.result[i].name
                    val price : String = it.result[i].price
                    val image : String = it.result[i].image
                    val description : String = it.result[i].description
                    val type : Int = it.result[i].type
                    listProduct.add(NewTypeProduct(id, name, price, image, description, type))
                }
                    val adapter = MnproductAdapter(context, listProduct, object : InterfacePhone{
                        //onClickItemPhone lay du lieu tu adapter
                        override fun onClickItemPhone(productPhone: NewTypeProduct) {
                            val product : NewTypeProduct = productPhone
                           onClickGoToEdit(product)

                        }

                        override fun onClickItemUser(user: ManageUser) {
                            TODO("Not yet implemented")
                        }
                    })
                    recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycler.adapter = adapter
                },
                {

                })
    }
    private fun onClickGoToEdit(productPhone : NewTypeProduct){
        //Gui du lieu ra activity
        iSendData!!.sendData(productPhone)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.imb_add_product -> {
                val fm : FragmentManager = requireFragmentManager()
                val ft : FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.frame_layout, fragmentAdd)
                ft.commit()
            }
        }
    }

}
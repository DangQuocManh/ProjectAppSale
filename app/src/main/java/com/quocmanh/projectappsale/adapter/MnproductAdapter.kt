package com.quocmanh.projectappsale.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.activity.admin.EditProduct
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.myinterface.ImageClick
import com.quocmanh.projectappsale.myinterface.InterfacePhone
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat


class MnproductAdapter :  RecyclerView.Adapter<MnproductAdapter.MnproductViewHolder> {

    private var context : Context
    private var itemProducts : MutableList<NewTypeProduct>
    private var interfacePhone : InterfacePhone
    private lateinit var loginCallApi : LoginCallApi

    constructor(context: Context?, itemProducts: MutableList<NewTypeProduct>, interfacePhone : InterfacePhone) : super() {
        this.context = context!!
        this.itemProducts = itemProducts
        this.interfacePhone = interfacePhone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MnproductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_mnproduct, parent, false)
        return MnproductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MnproductViewHolder, position: Int) {
        val itemProduct = itemProducts[position]
        Glide.with(holder.itemView.context)
            .load(itemProduct.image)
            .into(holder.imageProduct)
        holder.nameProduct.setText(itemProduct.name)
        val decimalFormat = DecimalFormat("###,###,###")
        holder.priceProduct.setText("Giá: " + decimalFormat.format(itemProduct.price.toLong()).toString() + " Đ")

        holder.cardView.setOnClickListener {
            interfacePhone.onClickItemPhone(itemProduct)
        }
        holder.setImageClick(object : ImageClick{
            override fun onImageClick(view: View, position: Int, value: Int) {
                if (value == 2) {//Xoa hang hoa
                    if (itemProducts.size > 0) {
                        val builderDialog = AlertDialog.Builder(context)
                        builderDialog.setTitle("Xác nhận")
                        builderDialog.setMessage("Xóa sản phẩm khỏi danh mục")
                        builderDialog.setPositiveButton(
                            "Yes",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                itemProducts.removeAt(position)
                                notifyDataSetChanged()
                                loginCallApi = RetrofitFactor.createRetrofit()
                                loginCallApi.deleteProduct(
                                    itemProduct.name
                                ).subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({

                                    },
                                        {

                                        })
                            })
                        builderDialog.setNegativeButton(
                            "No",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.cancel()
                            })
                        builderDialog.show()
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return itemProducts.size
    }

    class MnproductViewHolder: RecyclerView.ViewHolder, View.OnClickListener{
        val imageProduct : ImageView
        val nameProduct : TextView
        val priceProduct : TextView
        val editProduct : ImageButton
        val deleteProduct : ImageButton
        lateinit var imageClick: ImageClick
        val editFragment : EditProduct
        val cardView : CardView
        constructor(itemView: View) : super(itemView){
            imageProduct = itemView.findViewById(R.id.image_product)
            nameProduct = itemView.findViewById(R.id.tv_name_product)
            priceProduct = itemView.findViewById(R.id.tv_price_product)
            editProduct = itemView.findViewById(R.id.imb_edit_product)
            deleteProduct = itemView.findViewById(R.id.imb_delete_product)
            editProduct.setOnClickListener(this)
            deleteProduct.setOnClickListener(this)
            editFragment = EditProduct()
            cardView = itemView.findViewById(R.id.cardView)
        }
        @JvmName("setImageClick1")
        fun setImageClick(imageClick: ImageClick){
            this.imageClick = imageClick
        }
        override fun onClick(view: View?) {
           if(view == editProduct){
               //Sua san pham
               imageClick.onImageClick(view, adapterPosition, 1)
           }else if(view == deleteProduct){
               //Xoa san pham
               imageClick.onImageClick(view, adapterPosition, 2)
           }
        }
    }
}
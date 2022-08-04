package com.quocmanh.projectappsale.adapter

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.activity.CartActivity
import com.quocmanh.projectappsale.model.CartModel
import com.quocmanh.projectappsale.model.ModelCart
import com.quocmanh.projectappsale.myinterface.ImageClick
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat
import java.util.logging.Handler

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private var context : Context
    private var loginCallApi: LoginCallApi = RetrofitFactor.createRetrofit()
    private var itemCarts : MutableList<ModelCart> = mutableListOf()

    constructor(context: Context, itemCart: MutableList<ModelCart>) : super() {
        this.context = context
        this.itemCarts = itemCart
    }


    override fun getItemCount(): Int {
        return itemCarts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val itemCart = itemCarts[position]
        holder.nameCart.setText(itemCart.name)
        holder.countCart.setText(itemCart.count.toString())
        val decimal : DecimalFormat = DecimalFormat("###,###,###")
        holder.txtPrice.setText(decimal.format(itemCart.price.toDouble()).toString() + " Đ")
        Glide.with(holder.itemView.context)
            .load(itemCart.image)
            .into(holder.imgCart)
        holder.setImageClick(object : ImageClick {
            override fun onImageClick(view: View, position: Int, value: Int) {
                if(value == 1){
                    try {
                        if (itemCarts[position].count < 10) {
                            //Tang so luong san pham
                            var count: Int = itemCarts[position].count + 1
                            itemCarts[position].count = count
                            var countNew: Int = count
                            //Tinh gia cua san pham
                            var priceOneProduct: Int =
                                itemCarts[position].price.toInt() / (count - 1)
                            var priceNew: Int = priceOneProduct * countNew
                            itemCarts[position].price = priceNew.toString()

                            holder.txtPrice.setText(
                                decimal.format(itemCarts[position].price.toDouble())
                                    .toString() + " Đ"
                            )
                            holder.countCart.setText(countNew.toString())
                            //cartActivity.getTotalMoney(itemCarts)
                            //Cap nhap vao database
                            loginCallApi.updateCountProductCart(
                                itemCart.name, priceNew.toString(), countNew
                            ).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({

                                },
                                    {

                                    })
                        }
                    }catch (e : IndexOutOfBoundsException){
                        e.printStackTrace()
                    }
                }else if(value == 2){
                    try {
                        if (itemCarts[position].count > 0) {
                            var count: Int = itemCarts[position].count - 1
                            if(count == 0){
                                return
                            }
                            itemCarts[position].count = count
                            var countNew: Int = count

                            var priceOneProduct: Int =
                                itemCarts[position].price.toInt() / (count + 1)
                            var priceNew: Int = priceOneProduct * countNew
                            itemCarts[position].price = priceNew.toString()
                            holder.countCart.setText(countNew.toString())
                            holder.txtPrice.setText(
                                decimal.format(itemCarts[position].price.toDouble())
                                    .toString() + " Đ"
                            )
                            //Cap nhap vao database
                            loginCallApi.updateCountProductCart(
                                itemCart.name, priceNew.toString(), countNew
                            ).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({

                                },
                                    {

                                    })
                            //cartActivity.getTotalMoney(itemCarts)
                        }
                    }catch (e : IndexOutOfBoundsException){
                        e.printStackTrace()
                    }
                }
                else if(value == 3){
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Xác nhận")
                    builder.setMessage("Xóa sản phẩm khỏi giỏ hàng!")
                    builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        try {
                        if (itemCarts.size > 0) {
                            itemCarts.removeAt(position)
                            notifyDataSetChanged()
                            loginCallApi.deleteCart(
                                itemCart.name
                            )
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    {

                                    },
                                    {

                                    })
                        } else {
                            return@OnClickListener
                        }
                    }catch(e: IndexOutOfBoundsException){
                        e.printStackTrace()
                    }
                    })
                    builder.setNegativeButton("No", {
                        dialog, i ->
                        dialog.cancel()
                    })
                    builder.show()
                }
                //cartActivity.getTotalMoney(itemCarts)
               // holder.countCart.setText(itemCarts[position].count.toString())
            }
        })
    }

    class CartViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        val imgCart : ImageView
        val nameCart : TextView
        val removeCart : ImageView
        val countCart : TextView
        val addCart : ImageView
        val txtPrice : TextView
        val imbDelete : ImageButton
        lateinit var imageClick : ImageClick

        constructor(
            itemView: View
        ) : super(itemView) {
            imgCart = itemView.findViewById(R.id.imv_imagecart)
            nameCart = itemView.findViewById(R.id.txt_namecart)
            removeCart = itemView.findViewById(R.id.img_removecart)
            countCart = itemView.findViewById(R.id.txt_countcart)
            addCart = itemView.findViewById(R.id.img_addcart)
            txtPrice = itemView.findViewById(R.id.txt_price)
            imbDelete = itemView.findViewById(R.id.imb_delete)
            addCart.setOnClickListener(this)
            removeCart.setOnClickListener(this)
            imbDelete.setOnClickListener(this)

        }

        @JvmName("setImageClick1")
        fun setImageClick(imageClick : ImageClick){
            this.imageClick = imageClick
        }

        override fun onClick(view: View?) {
            if(view == addCart){
                //Cong gia tri 1
                imageClick.onImageClick(view, adapterPosition, 1)
            }else if(view == removeCart){
                //Tru gia tri 2
                imageClick.onImageClick(view, adapterPosition, 2)
            }else if(view == imbDelete){
                //Xoa item cart
                imageClick.onImageClick(view, adapterPosition, 3)
            }
        }

    }




}
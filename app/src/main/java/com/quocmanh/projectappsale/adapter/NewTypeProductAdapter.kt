package com.quocmanh.projectappsale.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.NewTypeProduct
import java.text.DecimalFormat

class NewTypeProductAdapter : RecyclerView.Adapter<NewTypeProductAdapter.NewTypeProductViewHolder> {
    private var itemNewProducts : MutableList<NewTypeProduct>

    constructor(itemNewProduct: MutableList<NewTypeProduct>) : super() {
        this.itemNewProducts = itemNewProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewTypeProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_new_product, parent, false)
        return NewTypeProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewTypeProductViewHolder, position: Int) {
        val itemNewProduct = itemNewProducts[position]
        Glide.with(holder.itemView.context)
            .load(itemNewProduct.image)
            .into(holder.imageProduct)
        holder.nameProduct.setText(itemNewProduct.name)
        var decimalFormat = DecimalFormat("###,###,###")
        holder.priceProduct.setText("Giá "+ decimalFormat.format(itemNewProduct.price.toDouble())+"Đ")
    }

    override fun getItemCount(): Int {
        return itemNewProducts.size
    }

    class NewTypeProductViewHolder : RecyclerView.ViewHolder{
        val imageProduct : ImageView
        val nameProduct : TextView
        val priceProduct : TextView

        constructor(
            itemView: View
        ) : super(itemView) {
            imageProduct = itemView.findViewById<ImageView>(R.id.iv_image_product)
            nameProduct = itemView.findViewById(R.id.tv_name_product)
            priceProduct = itemView.findViewById(R.id.tv_price_product)
        }
    }
}
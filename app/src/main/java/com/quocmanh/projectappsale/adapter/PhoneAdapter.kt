package com.quocmanh.projectappsale.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.myinterface.InterfacePhone
import java.text.DecimalFormat

class PhoneAdapter : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private var itemPhones : MutableList<NewTypeProduct> = mutableListOf()
    private var interfacePhone : InterfacePhone

    constructor(itemPhone: MutableList<NewTypeProduct>, listener: InterfacePhone) : super() {
        this.itemPhones = itemPhone
        this.interfacePhone = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_phone, parent, false)
        return PhoneViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val itemPhone = itemPhones[position]
        Glide.with(holder.itemView.context)
            .load(itemPhone.image)
            .into(holder.imagePhone)
        holder.namePhone.setText(itemPhone.name)
        val decimalFormat = DecimalFormat("###,###,###")
        holder.pricePhone.setText("Giá " + decimalFormat.format(itemPhone.price.toDouble()) + " Đ")
        holder.description.setText(itemPhone.description)
        holder.cardviewPhone.setOnClickListener(View.OnClickListener() {
            interfacePhone.onClickItemPhone(itemPhone)
        })
    }

    override fun getItemCount(): Int {
        return itemPhones.size
    }

    class PhoneViewHolder : RecyclerView.ViewHolder{
        val imagePhone : ImageView
        val namePhone : TextView
        val pricePhone : TextView
        val description : TextView
        val cardviewPhone : CardView

        constructor(
            itemView: View
        ) : super(itemView) {
            imagePhone = itemView.findViewById(R.id.iv_imagephone)
            namePhone = itemView.findViewById(R.id.tv_namephone)
            pricePhone = itemView.findViewById(R.id.tv_pricephone)
            description = itemView.findViewById(R.id.tv_description)
            cardviewPhone = itemView.findViewById(R.id.cv_phone)
        }
    }
}
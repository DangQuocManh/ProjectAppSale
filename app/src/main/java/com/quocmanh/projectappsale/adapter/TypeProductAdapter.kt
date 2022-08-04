package com.quocmanh.projectappsale.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.TypeProduct

class TypeProductAdapter (var context: Context, var listTypeProduct: MutableList<TypeProduct>) : BaseAdapter() {


    override fun getCount(): Int {
        return listTypeProduct.size
    }

    override fun getItem(position: Int): Any {
        return listTypeProduct[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
//        var viewHolder : ViewHolder? = null
//        if(view == null){
//            viewHolder = ViewHolder()
//            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val view = layoutInflater.inflate(R.layout.item_product, null)
//            viewHolder.product = view.findViewById(R.id.tv_product)
//            viewHolder.image = view.findViewById(R.id.iv_image)
//            view.setTag(viewHolder)
//        }else{
//            viewHolder = view.getTag() as ViewHolder?
//            viewHolder!!.product!!.setText(listTypeProduct.get(i).name)
//            Glide.with(context)
//                .load(listTypeProduct.get(i).image)
//                .into(viewHolder!!.image)
//        }
//        return view
//        var viewData: View
//        if (convertView == null) {
//            viewData = View.inflate(parent!!.context, R.layout.item_product, null)
//        } else viewData = convertView
//
//        var user = getItem(i) as TypeProduct
//        //viewData.findViewById<ImageView>(R.id.iv_image).setImageURI()
//        Glide.with(context)
//            .load(listTypeProduct.get(i).image)
//            .into(viewData.findViewById<ImageView>(R.id.iv_image))
//        viewData.findViewById<TextView>(R.id.tv_product).setText(user.name)
//        return viewData
        var view : View
        var viewHolder : ViewHolder
        if(convertView == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_product, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        var typeProduct: TypeProduct = getItem(position) as TypeProduct
        Glide.with(context)
            .load(typeProduct.image)
            .into(viewHolder.imageProduct)
        viewHolder.product.text = typeProduct.name
        return view
    }

    class ViewHolder(row : View){
        var imageProduct : ImageView
        var product : TextView
        init {
            imageProduct = row.findViewById(R.id.iv_image) as ImageView
            product = row.findViewById(R.id.tv_product) as TextView
        }
    }

}
package com.quocmanh.projectappsale.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.ManageUser
import com.quocmanh.projectappsale.myinterface.ImageClick
import com.quocmanh.projectappsale.myinterface.InterfacePhone
import com.quocmanh.projectappsale.myinterface.LoginCallApi
import com.quocmanh.projectappsale.retrofit.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.w3c.dom.Text

class MnuserAdapter : RecyclerView.Adapter<MnuserAdapter.MnuserViewHolder> {
    private lateinit var context : Context
    private lateinit var listUsers: MutableList<ManageUser>
    private lateinit var interfacePhone : InterfacePhone
    private lateinit var loginCallApi: LoginCallApi

    constructor(
        context: Context?,
        listUsers: MutableList<ManageUser>,
        interfacePhone: InterfacePhone
    ) : super() {
        this.context = context!!
        this.listUsers = listUsers
        this.interfacePhone = interfacePhone
    }

    class MnuserViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        val imageUser : ImageView
        val userName : TextView
        val email : TextView
        val mobile : TextView
        val type : TextView
        val deleteUser : ImageButton
        val cardViewUser : CardView
        lateinit var imageClick : ImageClick
        constructor(itemView: View) : super(itemView){
            imageUser = itemView.findViewById(R.id.image_user)
            userName = itemView.findViewById(R.id.tv_name_user)
            email = itemView.findViewById(R.id.tv_email_user)
            mobile = itemView.findViewById(R.id.tv_mobile_user)
            type = itemView.findViewById(R.id.tv_type_user)
            deleteUser = itemView.findViewById(R.id.imb_delete_user)
            deleteUser.setOnClickListener(this)
            cardViewUser = itemView.findViewById(R.id.cardViewUser)
        }
        @JvmName("setImageClick1")
        fun setImageClick(imageClick: ImageClick){
            this.imageClick = imageClick
        }
        override fun onClick(p0: View?) {
            if(p0 == deleteUser){
                imageClick.onImageClick(p0, adapterPosition, 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MnuserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_mnuser, parent, false)
        return MnuserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MnuserViewHolder, position: Int) {
        val itemUser = listUsers[position]
        holder.userName.setText("Tên tài khoản: " + itemUser.username)
        holder.email.setText("Email: " + itemUser.email)
        holder.mobile.setText("Số điện thoại: " + itemUser.mobile)
        holder.type.setText("Loại tài khoản: " + itemUser.type)
        holder.cardViewUser.setOnClickListener {
            interfacePhone.onClickItemUser(itemUser)
        }
        holder.setImageClick(object : ImageClick{
            override fun onImageClick(view: View, position: Int, value: Int) {
                if(value == 1){
                    if(listUsers.size > 0){
                        loginCallApi = RetrofitFactor.createRetrofit()
                        val builder = AlertDialog.Builder(context)
                            builder.setTitle("Xác nhận")
                            builder.setMessage("Xóa tài khoản người dùng!")
                            builder.setPositiveButton("Yes", {  dialogInterface, i ->
                                listUsers.removeAt(position)
                                notifyDataSetChanged()
                                loginCallApi.deleteUser(
                                    listUsers[position].username
                                ).subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({

                                    },
                                        {

                                        })
                            })
                            builder.setNegativeButton("No", {
                                dialogInterface, i ->
                                dialogInterface.cancel()
                            })
                        builder.show()
                    }
                }
            }

        })
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }
}
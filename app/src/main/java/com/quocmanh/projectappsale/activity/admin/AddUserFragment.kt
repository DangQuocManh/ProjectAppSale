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

/**
 * A simple [Fragment] subclass.
 * Use the [AddUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddUserFragment : Fragment(), View.OnClickListener {
    private lateinit var imageUser : ImageView
    private lateinit var userName: EditText
    private lateinit var emailUser : EditText
    private lateinit var mobileUser : EditText
    private lateinit var passUser : EditText
    private lateinit var typeUser : EditText
    private lateinit var btnAdd: Button
    private lateinit var loginCallApi: LoginCallApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add_user, container, false)
        init(view)
        return view
    }
    fun init(view : View){
        imageUser = view.findViewById(R.id.imv_image_add_user)
        userName = view.findViewById(R.id.edit_add_name_user)
        emailUser = view.findViewById(R.id.edit_add_email_user)
        mobileUser = view.findViewById(R.id.edit_add_phone_user)
        passUser = view.findViewById(R.id.edit_add_pass_user)
        typeUser = view.findViewById(R.id.edit_add_type_user)
        btnAdd = view.findViewById(R.id.btn_add_user)
        btnAdd.setOnClickListener(this)
        loginCallApi = RetrofitFactor.createRetrofit()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_add_user -> {
                val name : String = userName.text.toString().trim()
                val email : String = emailUser.text.toString().trim()
                val mobile : String = mobileUser.text.toString().trim()
                val pass : String = passUser.text.toString().trim()
                val type : String = typeUser.text.toString().trim()
                loginCallApi.insertUserAdmin(
                    name, email, mobile, pass, type
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccess == true){
                            Toast.makeText(context, "Thêm tài khoản thành công!", Toast.LENGTH_LONG).show()
                        }
                    },
                        {

                        })
            }
        }
    }

}
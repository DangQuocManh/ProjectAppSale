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
 * Use the [EditUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditUserFragment : Fragment(), View.OnClickListener {
    private lateinit var imageUser : ImageView
    private lateinit var userName: EditText
    private lateinit var emailUser : EditText
    private lateinit var mobileUser : EditText
    private lateinit var passUser : EditText
    private lateinit var typeUser : EditText
    private lateinit var btnEdit : Button
    private lateinit var idUser : String
    private lateinit var loginCallApi: LoginCallApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_user, container, false)
        init(view)
        getDataToUserFragment()
        return view
    }
    fun getDataToUserFragment(){
        idUser = arguments?.getString("id")!!
        val userName1 : String = arguments?.getString("username")!!
        val email : String = arguments?.getString("email")!!
        val mobile : String = arguments?.getString("mobile")!!
        val pass : String = arguments?.getString("pass")!!
        val type : String = arguments?.getString("type")!!
        userName.setText(userName1)
        emailUser.setText(email)
        mobileUser.setText(mobile)
        passUser.setText(pass)
        typeUser.setText(type)
    }
    fun init(view : View){
        imageUser = view.findViewById(R.id.imv_image_edit_user)
        userName = view.findViewById(R.id.edit_name_user)
        emailUser = view.findViewById(R.id.edit_email_user)
        mobileUser = view.findViewById(R.id.edit_phone_user)
        passUser = view.findViewById(R.id.edit_pass_user)
        typeUser = view.findViewById(R.id.edit_type_user)
        btnEdit = view.findViewById(R.id.btn_edit_user)
        btnEdit.setOnClickListener(this)
        loginCallApi = RetrofitFactor.createRetrofit()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_edit_user -> {
                val name : String = userName.text.toString().trim()
                val email : String = emailUser.text.toString().trim()
                val mobile : String = mobileUser.text.toString().trim()
                val pass : String = passUser.text.toString().trim()
                val type : String = typeUser.text.toString().trim()
                loginCallApi.updateUser(
                    idUser.toInt(), name, email, mobile, pass, type
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccess == true){
                            Toast.makeText(context, "Cập nhập thành công!", Toast.LENGTH_LONG).show()
                        }
                    },
                        {

                        })
            }
        }
    }

}
package com.quocmanh.projectappsale.activity.admin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.adapter.MnuserAdapter
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
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment(), View.OnClickListener {
    private lateinit var backImage : ImageButton
    private lateinit var addUser : ImageButton
    private lateinit var recyclerUser : RecyclerView
    private lateinit var loginCallApi: LoginCallApi
    private lateinit var listUsers : MutableList<ManageUser>
    private lateinit var iSendDataToEdit: ISendDataToEdit
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_user, container, false)
        init(view)
        getDataUser()
        return view
    }
    private fun init(view : View){
        backImage = view.findViewById(R.id.imb_back_user)
        addUser = view.findViewById(R.id.imb_add_user)
        recyclerUser = view.findViewById(R.id.rcv_user)
        loginCallApi = RetrofitFactor.createRetrofit()
        listUsers = mutableListOf()
        addUser.setOnClickListener(this)
    }
    private fun getDataUser(){
        loginCallApi.getManageUser(

        ).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var idUser : Int
                var nameUser : String = ""
                var email : String = ""
                var mobile : String = ""
                var password : String = ""
                var type : String = ""
                if(it.success == true){
                    for (i in 0..(it.result.size-1)){
                        idUser = it.result[i].id
                        nameUser = it.result[i].username
                        email = it.result[i].email
                        mobile = it.result[i].mobile
                        password = it.result[i].password
                        type = it.result[i].type
                        listUsers.add(ManageUser(idUser, nameUser, email, mobile, password, type))
                    }
                }
                val adapter = MnuserAdapter(context, listUsers, object : InterfacePhone{
                    override fun onClickItemPhone(productPhone: NewTypeProduct) {
                        TODO("Not yet implemented")
                    }

                    override fun onClickItemUser(user: ManageUser) {
                        goToDetailEditUser(user)
                    }


                })
                recyclerUser.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerUser.adapter = adapter
            },
                {

                })
    }
    fun goToDetailEditUser(user : ManageUser){
        iSendDataToEdit.senDataToEdit(user)
    }
    interface ISendDataToEdit{
        fun senDataToEdit(user :ManageUser)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iSendDataToEdit = activity as ISendDataToEdit
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.imb_add_user -> {
                val fm = requireFragmentManager()
                val ft : FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.frame_layout, AddUserFragment())
                ft.commit()
            }
        }
    }
}
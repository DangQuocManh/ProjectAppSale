package com.quocmanh.projectappsale.activity.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quocmanh.projectappsale.R
import com.quocmanh.projectappsale.model.NewTypeProduct
import java.lang.NullPointerException
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.quocmanh.projectappsale.model.ManageUser

class AdminActivity : AppCompatActivity(), ProductFragment.ISendDataListener, UserFragment.ISendDataToEdit {
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var productFragment: ProductFragment
    private lateinit var orderFragment: OrderFragment
    private lateinit var userFragment: UserFragment
    private lateinit var product1Fragment: Product1Fragment
    private lateinit var product2Fragment: Product2Fragment
    private lateinit var edit : EditProduct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        init()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, productFragment).commit()
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_product -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, productFragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.item_order -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, orderFragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.item_user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, userFragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.item_product1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, product1Fragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.item_product2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, product2Fragment).commit()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }
    private fun init(){
        bottomNavigation = findViewById(R.id.bottom_navigation)
        productFragment = ProductFragment()
        orderFragment = OrderFragment()
        userFragment = UserFragment()
        product1Fragment = Product1Fragment()
        product2Fragment = Product2Fragment()
        edit = EditProduct()
    }
    //Override senData tu ProductFragment de nhan du lieu va gui sang EditProduct
    override fun sendData(productPhone : NewTypeProduct) {
        //Dung bundle de chuyen du lieu sang fragment
        val bundle = Bundle()
        bundle.putString("id", productPhone.id.toString())
        bundle.putString("image", productPhone.image)
        bundle.putString("name", productPhone.name)
        bundle.putString("price", productPhone.price)
        bundle.putString("descryption", productPhone.description)
        bundle.putString("type", productPhone.type.toString())
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentTwo = EditProduct()
        fragmentTwo.arguments = bundle
        //Thay fragment hien tai bang fragment EditProduct
        transaction.replace(R.id.frame_layout, fragmentTwo)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun senDataToEdit(user: ManageUser) {
        val bundle = Bundle()
        bundle.putString("id", user.id.toString())
        bundle.putString("username", user.username.toString())
        bundle.putString("email", user.email.toString())
        bundle.putString("mobile", user.mobile.toString())
        bundle.putString("pass", user.password)
        bundle.putString("type", user.type)
        val transaction = this.supportFragmentManager.beginTransaction()
        val editUser : EditUserFragment = EditUserFragment()
        editUser.arguments = bundle
        transaction.replace(R.id.frame_layout, editUser)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}
package com.quocmanh.projectappsale.myinterface

import com.quocmanh.projectappsale.model.ManageUser
import com.quocmanh.projectappsale.model.NewTypeProduct
import com.quocmanh.projectappsale.model.User

interface InterfacePhone {
    fun onClickItemPhone(productPhone : NewTypeProduct)
    fun onClickItemUser(user : ManageUser)
}
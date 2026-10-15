package com.bluespacestudioz.quickbite.model

data class User(
    val userId : String = "",
    val name : String = "",
    val email : String = "",
    val role : String = UserRole.CUSTOMER.name,
    val createdAt : Long = System.currentTimeMillis()
)
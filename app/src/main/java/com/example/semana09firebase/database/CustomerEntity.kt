package com.example.semana09firebase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("customer")
data class CustomerEntity(
    @ColumnInfo("first_name") var firstName: String?,
    @ColumnInfo("last_name") var lastName: String?,
    @ColumnInfo("phone_number") var phoneNumber: String?
){
    @PrimaryKey(true)
    @ColumnInfo("customer_id") var customerId: Int = 0
}

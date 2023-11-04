package com.example.semana09firebase.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.semana09firebase.database.CustomerEntity
import com.example.semana09firebase.database.CustomerRepository

class ClienteViewModel (application: Application): AndroidViewModel(application) {
    private var repository = CustomerRepository(application)
    val customers = repository.getCustomers()

    fun saveCustomer(customerEntity: CustomerEntity){
        repository.insert(customerEntity)
    }
}
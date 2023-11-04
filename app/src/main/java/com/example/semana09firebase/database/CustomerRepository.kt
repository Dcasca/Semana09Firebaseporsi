package com.example.semana09firebase.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CustomerRepository(application: Application) {

    private val customerDao:
            CustomerDao? = CustomerDatabase
                .getInstance(application)?.customerDao()
    fun insert(customerEntity: CustomerEntity){
            if(customerDao!=null)
            InsertAsyncTask(customerDao).execute(customerEntity)
    }
    fun getCustomers(): LiveData<List<CustomerEntity>>{
        return customerDao?.getCustomerOrderByLastName() ?: MutableLiveData<List<CustomerEntity>>().apply{
            value = emptyList()
        }
    }

    private class InsertAsyncTask
        (private val customerDao: CustomerDao):AsyncTask<CustomerEntity, Void, Void>(){
        override fun doInBackground(vararg customers: CustomerEntity?): Void? {
            for(customer in customers){
                if(customer!=null)
                    customerDao.insert(customer)
            }
            return null
        }
    }
}
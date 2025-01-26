package com.example.accounts.repository

import com.example.accounts.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository:JpaRepository<Customer,Long>{
    fun findByMobileNumber(mobileNumber:String):Optional<Customer>
}
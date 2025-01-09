package com.example.accounts.entity

import jakarta.persistence.*
import lombok.Data

@Data
@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    var customerId:Long = 0L,
    var name:String = "",
    var email:String = "",
    @Column(name="mobile_number")
    var mobileNumber:String = "",
):BaseEntity()

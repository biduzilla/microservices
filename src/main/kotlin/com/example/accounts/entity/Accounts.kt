package com.example.accounts.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class Accounts(
    @Column(name="customer_id")
    var customerId:Long = 0L,

    @Id
    @Column(name="account_number")
    var accountNumber:Long = 0L,

    @Column(name="account_type")
    var accountType:String = "",

    @Column(name="branch_address")
    var branchAddress:String = "",
):BaseEntity()
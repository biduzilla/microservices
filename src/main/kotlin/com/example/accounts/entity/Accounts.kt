package com.example.accounts.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import lombok.Data

@Entity
@Data
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

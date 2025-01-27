package com.example.loans.entitiy

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.Data

@Entity
@Data
data class Loans(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var loanId: Long = 0L,
    var mobileNumber: String = "",
    var loanNumber: String = "",
    var loanType: String = "",
    var totalLoan: Int = 0,
    var amountPaid: Int = 0,
    var outstandingAmount: Int = 0
) : BaseEntity()

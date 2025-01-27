package com.example.loans.repository

import com.example.loans.entitiy.Loans
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LoansRepository : JpaRepository<Loans, Long> {
    fun findByMobileNumber(mobileNumber: String?): Optional<Loans>

    fun findByLoanNumber(loanNumber: String?): Optional<Loans>
}
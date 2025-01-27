package com.example.loans.service

import com.example.loans.dto.LoansDto


interface ILoansService {
    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    fun createLoan(mobileNumber: String)

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    fun fetchLoan(mobileNumber: String): LoansDto?

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    fun updateLoan(loansDto: LoansDto): Boolean

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    fun deleteLoan(mobileNumber: String): Boolean
}
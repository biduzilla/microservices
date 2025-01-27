package com.example.loans.service.impl

import com.example.loans.constants.LoansConstants
import com.example.loans.dto.LoansDto
import com.example.loans.entitiy.Loans
import com.example.loans.exception.LoanAlreadyExistsException
import com.example.loans.exception.ResourceNotFoundException
import com.example.loans.mapper.LoansMapper
import com.example.loans.repository.LoansRepository
import com.example.loans.service.ILoansService
import org.springframework.stereotype.Service
import java.util.*


@Service
class LoansServiceImpl(private val loansRepository: LoansRepository):ILoansService {
    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    override fun createLoan(mobileNumber: String) {
        val optionalLoans: Optional<Loans> = loansRepository.findByMobileNumber(mobileNumber)
        if (optionalLoans.isPresent) {
            throw LoanAlreadyExistsException("Loan already registered with given mobileNumber $mobileNumber")
        }
        loansRepository.save(createNewLoan(mobileNumber))
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private fun createNewLoan(mobileNumber: String): Loans {
        val newLoan = Loans()
        val randomLoanNumber = 100000000000L + Random().nextInt(900000000)
        newLoan.loanNumber = randomLoanNumber.toString()
        newLoan.mobileNumber = mobileNumber
        newLoan.loanType = LoansConstants.HOME_LOAN
        newLoan.totalLoan = LoansConstants.NEW_LOAN_LIMIT
        newLoan.amountPaid = 0
        newLoan.outstandingAmount = LoansConstants.NEW_LOAN_LIMIT
        return newLoan
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    override fun fetchLoan(mobileNumber: String): LoansDto? {
        val loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException(
                "Loan",
                "mobileNumber",
                mobileNumber
            )
        }
        return LoansMapper.mapToLoansDto(loans, LoansDto())
    }

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    override fun updateLoan(loansDto: LoansDto): Boolean {
        val loans = loansRepository.findByLoanNumber(loansDto.loanNumber).orElseThrow<ResourceNotFoundException> {
            ResourceNotFoundException(
                "Loan",
                "LoanNumber",
                loansDto.loanNumber
            )
        }
        LoansMapper.mapToLoans(loansDto, loans)
        loansRepository.save(loans)
        return true
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    override fun deleteLoan(mobileNumber: String): Boolean {
        val loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException(
                "Loan",
                "mobileNumber",
                mobileNumber
            )
        }
        loansRepository.deleteById(loans.loanId)
        return true
    }
}
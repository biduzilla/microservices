package com.example.loans.mapper

import com.example.loans.dto.LoansDto
import com.example.loans.entitiy.Loans


class LoansMapper {
    companion object{
        fun mapToLoansDto(loans: Loans, loansDto: LoansDto): LoansDto {
            loansDto.loanNumber = loans.loanNumber
            loansDto.loanType = loans.loanType
            loansDto.mobileNumber = loans.mobileNumber
            loansDto.totalLoan = loans.totalLoan
            loansDto.amountPaid = loans.amountPaid
            loansDto.outstandingAmount = loans.outstandingAmount
            return loansDto
        }

        fun mapToLoans(loansDto: LoansDto, loans: Loans): Loans {
            loans.loanNumber = loansDto.loanNumber
            loans.loanType = loansDto.loanType
            loans.mobileNumber = loansDto.mobileNumber
            loans.totalLoan = loansDto.totalLoan
            loans.amountPaid = loansDto.amountPaid
            loans.outstandingAmount = loansDto.outstandingAmount
            return loans
        }
    }
}
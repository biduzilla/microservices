package com.example.accounts.mapper

import com.example.accounts.dto.AccountsDTO
import com.example.accounts.entity.Accounts

class AccountsMapper {
    companion object {
        fun mapToAccountsDTO(accounts: Accounts): AccountsDTO {
            return AccountsDTO(
                accountType = accounts.accountType,
                branchAddress = accounts.branchAddress,
                accountNumber = accounts.accountNumber
            )
        }

        fun mapToAccounts(accountsDTO: AccountsDTO): Accounts {
            return Accounts(
                accountType = accountsDTO.accountType,
                branchAddress = accountsDTO.branchAddress,
                accountNumber = accountsDTO.accountNumber
            )
        }
    }
}
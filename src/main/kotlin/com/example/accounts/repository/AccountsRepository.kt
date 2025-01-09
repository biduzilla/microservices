package com.example.accounts.repository

import com.example.accounts.entity.Accounts
import com.example.accounts.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository:JpaRepository<Accounts,Long>{
}
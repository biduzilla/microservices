package com.example.accounts.repository

import com.example.accounts.entity.Accounts
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountsRepository:JpaRepository<Accounts,Long>{

    fun findByCustomerId(customerId:Long):Optional<Accounts>

    @Transactional
    @Modifying
    fun deleteByCustomerId(customerId:Long)
}
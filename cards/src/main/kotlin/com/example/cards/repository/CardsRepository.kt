package com.example.cards.repository

import com.example.cards.entity.Cards
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface CardsRepository : JpaRepository<Cards, Long> {
    fun findByMobileNumber(mobileNumber: String?): Optional<Cards>
    fun findByCardNumber(cardNumber: String?): Optional<Cards>
}
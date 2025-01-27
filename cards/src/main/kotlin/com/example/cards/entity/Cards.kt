package com.example.cards.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.Data

@Entity
@Data
data class Cards(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cardId: Long = 0L,
    var mobileNumber: String = "",
    var cardNumber: String = "",
    var cardType: String = "",
    var totalLimit: Int = 0,
    var amountUsed: Int = 0,
    var availableAmount: Int = 0
) : BaseEntity()

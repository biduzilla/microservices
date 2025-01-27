package com.example.cards.mapper

import com.example.cards.dto.CardsDto
import com.example.cards.entity.Cards


class CardsMapper {
    companion object {
        fun mapToCardsDto(cards: Cards, cardsDto: CardsDto): CardsDto {
            cardsDto.cardNumber = cards.cardNumber
            cardsDto.cardType = cards.cardType
            cardsDto.mobileNumber = cards.mobileNumber
            cardsDto.totalLimit = cards.totalLimit
            cardsDto.availableAmount = cards.availableAmount
            cardsDto.amountUsed = cards.amountUsed
            return cardsDto
        }

        fun mapToCards(cardsDto: CardsDto, cards: Cards): Cards {
            cards.cardNumber = cardsDto.cardNumber
            cards.cardType = cardsDto.cardType
            cards.mobileNumber = cardsDto.mobileNumber
            cards.totalLimit = cardsDto.totalLimit
            cards.availableAmount = cardsDto.availableAmount
            cards.amountUsed = cardsDto.amountUsed
            return cards
        }
    }

}
package com.example.cards.service.impl

import com.example.cards.constants.CardsConstants
import com.example.cards.dto.CardsDto
import com.example.cards.entity.Cards
import com.example.cards.exception.CardAlreadyExistsException
import com.example.cards.exception.ResourceNotFoundException
import com.example.cards.mapper.CardsMapper
import com.example.cards.repository.CardsRepository
import com.example.cards.service.ICardsService
import org.springframework.stereotype.Service
import java.util.*


@Service
class CardsServiceImpl(private val cardsRepository: CardsRepository):ICardsService {
    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    override fun createCard(mobileNumber: String) {
        val optionalCards: Optional<Cards> = cardsRepository.findByMobileNumber(mobileNumber)
        if (optionalCards.isPresent) {
            throw CardAlreadyExistsException("Card already registered with given mobileNumber $mobileNumber")
        }
        cardsRepository.save(createNewCard(mobileNumber))
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private fun createNewCard(mobileNumber: String): Cards {
        val newCard = Cards()
        val randomCardNumber = 100000000000L + Random().nextInt(900000000)
        newCard.cardNumber = randomCardNumber.toString()
        newCard.mobileNumber = mobileNumber
        newCard.cardType = CardsConstants.CREDIT_CARD
        newCard.totalLimit = CardsConstants.NEW_CARD_LIMIT
        newCard.amountUsed = 0
        newCard.availableAmount = CardsConstants.NEW_CARD_LIMIT
        return newCard
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    override fun fetchCard(mobileNumber: String): CardsDto? {
        val cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException(
                "Card",
                "mobileNumber",
                mobileNumber
            )
        }
        return CardsMapper.mapToCardsDto(cards, CardsDto())
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    override fun updateCard(cardsDto: CardsDto): Boolean {
        val cards = cardsRepository.findByCardNumber(cardsDto.cardNumber).orElseThrow<ResourceNotFoundException> {
            ResourceNotFoundException(
                "Card",
                "CardNumber",
                cardsDto.cardNumber
            )
        }
        CardsMapper.mapToCards(cardsDto, cards)
        cardsRepository.save(cards)
        return true
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    override fun deleteCard(mobileNumber: String): Boolean {
        val cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException(
                "Card",
                "mobileNumber",
                mobileNumber
            )
        }
        cardsRepository.deleteById(cards.cardId)
        return true
    }
}
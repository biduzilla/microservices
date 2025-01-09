package com.example.accounts.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import lombok.Data
import java.time.LocalDateTime

//MappedSuperclass - essa entidade vai ser a super class para as outras entidades
@MappedSuperclass
@Data
abstract class BaseEntity(
    @Column(updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(updatable = false)
    var createdBy: String = "",

    @Column(updatable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(updatable = false)
    var updatedBy: String = "",
)

package com.example.cqrs.events

import java.time.Instant

interface DomainEvent {
    fun occuredAt(): Instant
}
package com.example.cqrs.events

import java.time.Instant

open class UserActivated(val `when`: Instant) : DomainEvent {
    override fun occuredAt(): Instant {
        return `when`
    }
}
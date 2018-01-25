package com.example.cqrs.events

import java.time.Instant

open class UserDeactivated(val `when`: Instant) : DomainEvent {
    override fun occuredAt(): Instant {
        return `when`
    }
}
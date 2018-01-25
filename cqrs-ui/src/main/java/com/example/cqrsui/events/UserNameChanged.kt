package com.example.cqrs.events

import java.time.Instant

open class UserNameChanged(val newNickName: String, val `when`: Instant) : DomainEvent {
    override fun occuredAt(): Instant {
        return `when`
    }
}
package com.example.cqrs.events

import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.Publisher
import org.springframework.stereotype.Component


@Component
class EventPublisher {

    @Publisher(channel = Source.OUTPUT)
    fun sendEvent(event: DomainEvent): DomainEvent {
        System.out.println("send: " + event)
        return event
    }
}
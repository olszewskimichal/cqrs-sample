package com.example.cqrs.events

import com.example.cqrs.user.User
import com.example.cqrs.user.UserRepository
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Component
class EventSourcedUserRepository(private val eventPublisher: EventPublisher) : UserRepository {

    private val users = ConcurrentHashMap<UUID, MutableList<DomainEvent>>()

    override fun save(user: User) {
        val changes = user.changes
        val copyChanges = changes.stream().collect(Collectors.toList())
        val currentChanges = users.getOrDefault(user.getUUID(), ArrayList())
        currentChanges.addAll(changes)
        users[user.getUUID()] = currentChanges
        user.flushChanges()
        copyChanges.forEach { v -> eventPublisher.sendEvent(v) }
    }

    override fun find(uuid: UUID): User {
        return User.recreateFrom(uuid, users.getOrDefault(uuid, ArrayList()))
    }

    fun find(uuid: UUID, timestamp: Instant): User {
        return User.recreateFrom(uuid, users.getOrDefault(uuid, ArrayList()).filter { v -> !v.occuredAt().isAfter(timestamp) } as MutableList<DomainEvent>)
    }

}
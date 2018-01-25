package com.example.cqrs.user

import java.util.*
import java.util.concurrent.ConcurrentHashMap


class InMemoryUserRepo : UserRepository {

    private val users = ConcurrentHashMap<UUID, User>()

    override fun save(user: User) {
        users[user.getUUID()] = user
    }

    override fun find(uuid: UUID): User {
        return users[uuid]!!
    }
}
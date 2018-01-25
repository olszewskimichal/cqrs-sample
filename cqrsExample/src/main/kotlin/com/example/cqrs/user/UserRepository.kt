package com.example.cqrs.user

import java.util.*

interface UserRepository {
    fun save(user: User)
    fun find(uuid: UUID): User
}
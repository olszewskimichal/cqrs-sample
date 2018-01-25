package com.example.cqrs.user

import com.example.cqrs.events.DomainEvent
import com.example.cqrs.events.UserActivated
import com.example.cqrs.events.UserDeactivated
import com.example.cqrs.events.UserNameChanged
import java.time.Instant
import java.util.*


class User(private val uuid: UUID) {

    internal enum class UserState {
        INITIALIZED, ACTIVATED, DEACTIVATED
    }

    private var state = UserState.INITIALIZED
    private var nickname = ""
    val changes = ArrayList<DomainEvent>()

    fun activate() {
        if (isActivated()) {
            throw IllegalStateException()
        }
        userActivated(UserActivated(Instant.now()))
    }

    private fun userActivated(event: UserActivated) {
        state = UserState.ACTIVATED
        changes.add(event)
    }

    fun deactivate() {
        if (isDeactivated())
            throw IllegalStateException()
        userDeactivated(UserDeactivated(Instant.now()))
    }

    private fun userDeactivated(event: UserDeactivated) {
        state = UserState.DEACTIVATED
        changes.add(event)
    }

    fun changeNickNameTo(name: String) {
        if (isDeactivated()) {
            throw IllegalStateException()
        }
        usernameChanged(UserNameChanged(name, Instant.now()))
    }

    private fun usernameChanged(event: UserNameChanged) {
        nickname = event.newNickName
        changes.add(event)
    }

    fun isActivated(): Boolean {
        return state == UserState.ACTIVATED
    }

    private fun isDeactivated(): Boolean {
        return state == UserState.DEACTIVATED
    }

    fun getNickName(): String {
        return nickname
    }

    fun getUUID(): UUID {
        return uuid
    }

    fun flushChanges() {
        changes.clear()
    }

    fun handleEvent(event: DomainEvent) {
        when (event) {
            is UserNameChanged -> usernameChanged(event)
            is UserActivated -> userActivated(event)
            is UserDeactivated -> userDeactivated(event)
        }
    }

    companion object {
        fun recreateFrom(uuid: UUID, events: MutableList<DomainEvent>): User {
            val user = User(uuid)
            events.forEach { it ->
                user.handleEvent(it)
            }
            return user
        }
    }
}
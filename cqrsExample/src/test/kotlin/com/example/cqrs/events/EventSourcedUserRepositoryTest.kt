package com.example.cqrs.events

import com.example.cqrs.user.User
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.util.*


class EventSourcedUserRepositoryTest {

    var userRepository: EventSourcedUserRepository = EventSourcedUserRepository(EventPublisher())

    @Test
    fun shouldBeAbleToSaveAndLoadUser() {
        //given
        val randomUUID = UUID.randomUUID()
        val user = User(randomUUID)
        user.activate()
        user.changeNickNameTo("Barry")
        //when
        userRepository.save(user)
        val loaded = userRepository.find(randomUUID)
        //then
        assertEquals(loaded.isActivated(),true)
        assertEquals(loaded.getNickName(),"Barry")
    }

    @Test
    fun shouldBeAbleToLoadStateFromHistoricTimeStamp() {
        //given
        val randomUUID = UUID.randomUUID()
        val user = User(randomUUID)
        user.activate()
        user.changeNickNameTo("Barry")
        //when
        userRepository.save(user)
        Thread.sleep(100)
        val instant = Instant.now()
        Thread.sleep(100)
        user.changeNickNameTo("John")
        userRepository.save(user)
        //then
        assertEquals(userRepository.find(randomUUID).getNickName(),"John")
        assertEquals(userRepository.find(randomUUID, instant).getNickName(),"Barry")

    }

}
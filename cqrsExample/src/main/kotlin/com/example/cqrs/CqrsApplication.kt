package com.example.cqrs

import com.example.cqrs.user.User
import com.example.cqrs.user.UserRepository
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.*

@EnableBinding(Source::class)
@SpringBootApplication
@EnableScheduling
class CqrsApplication(private val repository: UserRepository) {

    @Scheduled(fixedRate = 2000L)
    fun randomUsers() {
        val user = User(UUID.randomUUID())
        user.activate()
        user.changeNickNameTo("Name" + Random().nextInt())
        repository.save(user)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(CqrsApplication::class.java, *args)
}

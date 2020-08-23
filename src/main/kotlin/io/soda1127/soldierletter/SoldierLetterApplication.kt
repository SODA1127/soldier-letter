package io.soda1127.soldierletter

import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SoldierLetterApplication {
    companion object : KLogging()
}

fun main(args: Array<String>) {
    runApplication<SoldierLetterApplication>(*args)
}

package io.soda1127.soldierletter

import mu.KLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SoldierLetterApplication {
    companion object : KLogging()
}

fun main(args: Array<String>) {
    val app = SpringApplication(SoldierLetterApplication::class.java)
    app.addListeners(ApplicationPidFileWriter())   // pid 를 작성하는 역할을 하는 클래스 선언
    app.run(*args)
}

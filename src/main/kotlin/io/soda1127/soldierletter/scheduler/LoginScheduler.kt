package io.soda1127.soldierletter.scheduler

import io.soda1127.soldierletter.service.ClientService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.NamedKLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component("LoginScheduler")
class LoginScheduler {

    companion object : NamedKLogging("LoginScheduler")
    var dataReceived = false
    @Autowired
    private lateinit var clientService: ClientService

    @Scheduled(cron = "* 50 02 ? * *", zone = "Asia/Seoul")
    private fun login() = runBlocking {
        withContext(Dispatchers.IO) {
            if (!dataReceived) {
                dataReceived = true
                clientService.login("dlrlwjd1127@naver.com", "@Dlrlwjd1148")
            }
        }
    }
}
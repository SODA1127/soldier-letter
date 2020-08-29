package io.soda1127.soldierletter.service

import io.soda1127.soldierletter.model.Cookie
import io.soda1127.soldierletter.model.Message
import io.soda1127.soldierletter.model.Soldier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

/**
 * 더캠프 클라이언트
 * @class Client
 */

@Service
internal class ClientService {
    var cookies: Cookie? = null

    @Autowired
    lateinit var loginService: LoginService
    /*@Autowired
    lateinit var fetchSoldierService: FetchSoldierService*/
    @Autowired
    lateinit var sendMessageService: SendMessageService

    /**
     * 로그인을 강제한다.
     */
    fun enforceLogin() {
        if (cookies == null || cookies?.iuid == null || cookies?.token == null) {
            throw IllegalArgumentException("로그인이 필요한 서비스입니다.")
        }
    }

    /**
     * 로그인한다.
     * @param id 계정 아이디
     * @param password 계정 비밀번호
     */
    fun login(id: String, password: String) {
        this.cookies = loginService.login(id, password)
    }

    /**
     * 군인 정보를 가져온다.
     * @param soldier - 확인할 군인 정보
     */
    /*fun fetchSoldiers(soldier: Soldier): Mono<Soldier> {
        this.enforceLogin()
        return Mono.just(fetchSoldierService.fetchSoldiers(cookies!!, soldier))
    }*/

    /**
     * 인터넷 편지를 전송한다.
     * @param soldier - 훈련병 정보
     * @param message - 인터넷 편지 정보
     */
    fun sendMessage(soldier: Soldier, message: Message) {
        this.enforceLogin()
        sendMessageService.sendMessage(cookies!!, soldier, message)
    }
}
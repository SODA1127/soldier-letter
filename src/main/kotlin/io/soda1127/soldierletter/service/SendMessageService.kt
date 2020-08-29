package io.soda1127.soldierletter.service

import io.soda1127.soldierletter.model.Cookie
import io.soda1127.soldierletter.model.Message
import io.soda1127.soldierletter.model.Soldier
import org.springframework.stereotype.Service

/**
 * 인터넷 편지를 전송한다.
 * @param cookies - 세션 식별을 위한 쿠키
 * @param trainee - 훈련병 정보
 * @param message - 인터넷 편지 정보
 */

@Service
internal class SendMessageService {

    fun sendMessage(cookies: Cookie, trainee: Soldier, message: Message) {

    }

}
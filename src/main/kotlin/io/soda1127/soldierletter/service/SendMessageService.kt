package io.soda1127.soldierletter.service

import com.google.gson.Gson
import io.soda1127.soldierletter.model.*
import io.soda1127.soldierletter.model.Cookie
import io.soda1127.soldierletter.model.Soldier
import io.soda1127.soldierletter.model.SoldierResponse
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.LaxRedirectStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

/**
 * 인터넷 편지를 전송한다.
 * @param cookies - 세션 식별을 위한 쿠키
 * @param trainee - 훈련병 정보
 * @param message - 인터넷 편지 정보
 */

@Service
internal class SendMessageService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun sendMessage(cookies: Cookie, trainee: Soldier, message: Message): Boolean {
        val factory = HttpComponentsClientHttpRequestFactory()
        val httpClient: HttpClient = HttpClientBuilder.create()
            .setRedirectStrategy(LaxRedirectStrategy())
            .build()
        factory.httpClient = httpClient
        restTemplate.requestFactory = factory

        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            "${StaticValue.URL}/consolLetter/insertConsolLetterA.do?",
            HttpMethod.POST,
            HttpEntity(LinkedMultiValueMap<String, String>().apply {
                add("traineeMgrSeq", trainee.traineeMgrSeq)
                add("sympathyLetterContent", message.sympathyLetterContent)
                add("sympathyLetterSubject", message.sympathyLetterSubject)
                add("boardDiv", "sympathyLetter")
                add("tempSaveYn", "N")
            }, HttpHeaders().apply {
                add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                cookies.cookieList?.forEach {
                    add(HttpHeaders.COOKIE, it)
                }
                add(HttpHeaders.COOKIE, "${cookies.iuid}; ${cookies.token}")
            }),
            object : ParameterizedTypeReference<String>() {})

        val sendResponse = Gson().fromJson(responseEntity.body, SendResponse::class.java)
        return responseEntity.statusCode == HttpStatus.OK && sendResponse.resultCd == "0000"
    }

}
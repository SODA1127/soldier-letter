package io.soda1127.soldierletter.service

import io.soda1127.soldierletter.model.Cookie
import io.soda1127.soldierletter.model.LoginRequest
import io.soda1127.soldierletter.model.SessionResponse
import io.soda1127.soldierletter.model.StaticValue.URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate


/**
{
"resultCd": "0000",
"resultMsg": "정상처리되었습니다.",
"reCertYn": "Y",
"iuid": "4774446"
}
 */
@Service
internal class LoginService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun login(id: String, password: String): Cookie? {
        val responseEntity: ResponseEntity<SessionResponse> = restTemplate.exchange(
            "${URL}/login/loginA.do",
            HttpMethod.POST,
            HttpEntity(LinkedMultiValueMap<String, String>().apply {
                add("state", "email-login")
                add("autoLoginYn", "N")
                add("userId", id)
                add("userPwd", password)
            }, HttpHeaders().apply {
                add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
            }),
            object : ParameterizedTypeReference<SessionResponse>() {})
        return extractCookies(responseEntity.headers["set-cookie"])
    }

    fun extractCookies(cookies: List<String>?): Cookie {
        if (cookies.isNullOrEmpty()) {
            throw Exception("The cookie values in the header are empty.")
        }

        val iuid = cookies.find { it.contains("iuid=") }
        val token = cookies.find { it.contains("Token=") }

        /*return {
            iuid: iuid.slice(0, iuid.indexOf(';')),
            token: token.slice(0, token.indexOf(';')),
        };*/
        return Cookie(
            iuid = iuid?.substring(0, iuid.indexOf(";"))?.replace("iuid=", ""),
            token = token?.substring(0, token.indexOf(";"))?.replace("Token=", "")
        )
    }
}
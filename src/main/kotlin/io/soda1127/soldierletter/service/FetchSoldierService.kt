package io.soda1127.soldierletter.service

import com.google.gson.Gson
import io.soda1127.soldierletter.model.Cookie
import io.soda1127.soldierletter.model.Soldier
import io.soda1127.soldierletter.model.SoldierResponse
import io.soda1127.soldierletter.model.StaticValue
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

@Service
internal class FetchSoldierService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    /**
     * 군인 정보를 가져온다.
     * @param cookies - 세션 식별을 위한 쿠키
     * @param soldier - 확인할 군인 정보
     */

    fun fetchSoldiers(cookies: Cookie, soldier: Soldier): Soldier? {
        val factory = HttpComponentsClientHttpRequestFactory()
        val httpClient: HttpClient = HttpClientBuilder.create()
            .setRedirectStrategy(LaxRedirectStrategy())
            .build()
        factory.httpClient = httpClient
        restTemplate.requestFactory = factory

        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            "${StaticValue.URL}/main/cafeCreateCheckA.do",
            HttpMethod.POST,
            HttpEntity(LinkedMultiValueMap<String, String>().apply {
                add("name", soldier.traineeNm)
                add("birth", soldier.birth)
                add("enterDate", soldier.regDate)
            }, HttpHeaders().apply {
                add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                cookies.cookieList?.forEach {
                    add(HttpHeaders.COOKIE, it)
                }
                add(HttpHeaders.COOKIE, "${cookies.iuid}; ${cookies.token}")
            }),
            object : ParameterizedTypeReference<String>() {})
        val soldierResponse = Gson().fromJson(responseEntity.body, SoldierResponse::class.java)
        println("responseEntity : $soldierResponse")
        return if (soldierResponse.listResult.isNullOrEmpty()) null else soldierResponse.listResult[0]
    }
}

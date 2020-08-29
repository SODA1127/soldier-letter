/*
package io.soda1127.soldierletter.service

import io.soda1127.soldierletter.model.Cookie
import io.soda1127.soldierletter.model.Soldier
import io.soda1127.soldierletter.model.StaticValue.URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
internal class FetchSoldierService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    */
/**
     * 군인 정보를 가져온다.
     * @param cookies - 세션 식별을 위한 쿠키
     * @param soldier - 확인할 군인 정보
     *//*

    fun fetchSoldiers(cookies: Cookie, soldier: Soldier): Soldier {

        */
/*val responseEntity: ResponseEntity<List<POJO>> = restTemplate.exchange<List<POJO>>(
            "${URL}/main/cafeCreateCheckA.do",
            HttpMethod.POST,
            null,
            object : ParameterizedTypeReference<List<POJO?>?>() {})
        val pojoObjList: List<POJO> = responseEntity.body!!*//*


        */
/*const options = {
            url: buildRequestUrl('main/cafeCreateCheckA.do'),
            method: 'POST',
            json: true,
            headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            Cookie: `${cookies.iuid}; ${cookies.token}`,
        },
            form: {
            name: soldier.getName(),
            birth: soldier.getBirth(),
            enterDate: soldier.getEnterDate(),
        }*//*



        */
/*const response = await requestPromise(options, (err, res, body) => {
            if (err) {
                throw new Error(err);
            }

            addLog('fetchSoldier', `${res.statusCode} ${res.statusMessage}`);

            if (res.statusCode === 200 && body.resultCd !== '9999') {
                throw new Error(body.resultMsg || '알 수 없는 에러.');
            }
        });

        if (!response) {
            throw new Error('응답 값이 없습니다.');
        }

        const result: Soldier[] = response.listResult.map((fetchedSoldierInfo) => {
            const { traineeMgrSeq } = fetchedSoldierInfo;
            const clonedSoldier = soldier.clone();
            clonedSoldier.setTraineeMgrSeq(traineeMgrSeq);
            return clonedSoldier;
        });

        if (!result || result.length === 0) {
            throw new Error('해당하는 군인을 찾을 수 없습니다.');
        }

        return result;
    }*//*

        return null
    }

}*/

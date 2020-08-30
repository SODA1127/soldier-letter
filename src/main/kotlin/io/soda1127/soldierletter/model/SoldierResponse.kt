package io.soda1127.soldierletter.model

/**
{
"traineeMgrSeq": 1011157,
"trainUnitEduSeq": 4840,
"traineeNm": "장우찬",
"traineeNum": "000",
"birth": "tY1LjyE+ONTNDXAPVKwEqA==",
"platoonNum": "0",
"livingRoom": null,
"missSoldierAlarmYn": "N",
"useYn": "Y",
"regUser": 4724836,
"regDate": "20200811",
"regTime": "172035",
"uptUser": 4724836,
"uptDate": "20200811",
"uptTime": "173002",
"trainUnitEduNm": "'20-12기 (8. 3. 입영)",
"enterWeek": 4,
"mainDDay": "10",
"mainDate": "2020.09.09"
}
 */

internal data class SoldierResponse(
    val resultCd: String,
    val resultMsg: String,
    val listResult: List<Soldier>
)
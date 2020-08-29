package io.soda1127.soldierletter.model

/**
{
"resultCd": "0000",
"resultMsg": "정상처리되었습니다.",
"reCertYn": "Y",
"iuid": "4774446"
}
 */

internal data class SessionResponse(
    val resultCd: String,
    val resultMsg: String,
    val reCertYn: String,
    val iuid: String
)
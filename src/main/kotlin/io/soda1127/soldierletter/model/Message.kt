package io.soda1127.soldierletter.model

data class Message(
    private val sympathyLetterSubject: String, // 편지 제목
    private val sympathyLetterContent: String, // 편지 내용
    private val traineeMgrSeq: String // 훈련병 식별 코드
)
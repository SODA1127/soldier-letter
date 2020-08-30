package io.soda1127.soldierletter.model

data class Message(
    val sympathyLetterSubject: String, // 편지 제목
    val sympathyLetterContent: String, // 편지 내용
    val traineeMgrSeq: String // 훈련병 식별 코드
)
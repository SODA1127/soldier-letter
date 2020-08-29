package io.soda1127.soldierletter.model

data class LoginRequest(
    val state: String,
    val autoLoginYn: String,
    val userId: String,
    val userPwd: String
)
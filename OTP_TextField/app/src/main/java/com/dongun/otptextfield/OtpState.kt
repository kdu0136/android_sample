package com.dongun.otptextfield

data class OtpState(
    val code: List<Int?> = List(4) { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null,
)

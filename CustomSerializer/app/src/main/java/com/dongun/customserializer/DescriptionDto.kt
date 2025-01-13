package com.dongun.customserializer

import kotlinx.serialization.Serializable

@Serializable
data class DescriptionDto(
    val value: String,
)

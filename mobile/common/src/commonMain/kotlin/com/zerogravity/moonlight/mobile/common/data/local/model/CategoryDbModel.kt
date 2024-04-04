package com.zerogravity.moonlight.mobile.common.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDbModel(
    val id: String,
    val title: String,
    val description: String
)
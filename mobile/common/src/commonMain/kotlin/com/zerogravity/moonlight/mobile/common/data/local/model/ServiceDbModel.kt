package com.zerogravity.moonlight.mobile.common.data.local.model

import kotlinx.serialization.Serializable

@Serializable
class ServiceDbModel(
    val id: String,
    val categoryId: String,
    val title: String,
    val description: String,
    val price: Double
)
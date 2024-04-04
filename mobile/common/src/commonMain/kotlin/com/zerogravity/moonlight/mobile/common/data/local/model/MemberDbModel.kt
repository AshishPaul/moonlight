package com.zerogravity.moonlight.mobile.common.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val id: String,
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val dateCreated: Long,
    val dateUpdated: Long
)

@Serializable
data class NewMember(
    val id: String? = null,
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)


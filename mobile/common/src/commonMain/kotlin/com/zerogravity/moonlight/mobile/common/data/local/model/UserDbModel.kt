package com.zerogravity.moonlight.mobile.common.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDbModel(
    val id: String,
    val givenName: String,
    val familyName: String,
    val email: String,
    val phoneNumber: String,
    val profilePictureUrl: String,
)
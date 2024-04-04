package com.zerogravity.moonlight.mobile.common.domain.mapper

import com.zerogravity.moonlight.mobile.common.data.local.model.ServiceDbModel
import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel
import com.zerogravity.moonlight.shared.domain.model.dto.Service
import com.zerogravity.moonlight.shared.domain.model.dto.User

fun UserDbModel.dto() = User(
    id = this.id,
    givenName = this.givenName,
    familyName = this.familyName,
    email = this.email,
    phoneNumber = this.phoneNumber,
    profilePictureUrl = this.profilePictureUrl
)

fun User.userDbModel() = UserDbModel(
    id = this.id,
    givenName = this.givenName,
    familyName = this.familyName,
    email = this.email,
    phoneNumber = this.phoneNumber,
    profilePictureUrl = this.profilePictureUrl
)
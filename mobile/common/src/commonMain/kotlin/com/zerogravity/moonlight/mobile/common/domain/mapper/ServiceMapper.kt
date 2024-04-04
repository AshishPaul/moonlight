package com.zerogravity.moonlight.mobile.common.domain.mapper

import com.zerogravity.moonlight.mobile.common.data.local.model.ServiceDbModel
import com.zerogravity.moonlight.shared.domain.model.dto.Service

fun ServiceDbModel.dto() = Service(
    id = id,
    categoryId = categoryId,
    title = title,
    description = description,
    price = price
)

fun Service.serviceDbModel() = ServiceDbModel(
    id = id,
    categoryId = categoryId,
    title = title,
    description = description,
    price = price
)
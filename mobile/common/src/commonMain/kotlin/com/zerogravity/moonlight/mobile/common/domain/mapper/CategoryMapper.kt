package com.zerogravity.moonlight.mobile.common.domain.mapper

import com.zerogravity.moonlight.mobile.common.data.local.model.CategoryDbModel
import com.zerogravity.moonlight.shared.domain.model.dto.Category

fun CategoryDbModel.dto() = Category(
    id = id,
    title = title,
    description = description
)

fun Category.categoryDbModel() = CategoryDbModel(
    id = id,
    title = title,
    description = description,
)
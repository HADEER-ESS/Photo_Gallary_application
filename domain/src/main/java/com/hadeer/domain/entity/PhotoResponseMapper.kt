package com.hadeer.domain.entity

 fun PhotosItem.toGetData(): PhotoModel{
    return PhotoModel(
        id = id,
        height = height,
        width = width,
        url = src?.original,
        alt = alt
    )
}
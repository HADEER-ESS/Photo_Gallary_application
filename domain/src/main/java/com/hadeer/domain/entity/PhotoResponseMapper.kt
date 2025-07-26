package com.hadeer.domain.entity

 fun PhotosItem.toGetData(): PhotoModel{
    return PhotoModel(
        id = id,
        url = src?.original,
        alt = alt
    )
}

fun Photo.toMap():PhotoModel{
    return PhotoModel(
        id = id,
        url = url,
        alt = alt
    )
}
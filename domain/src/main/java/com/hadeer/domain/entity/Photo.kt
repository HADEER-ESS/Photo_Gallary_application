package com.hadeer.domain.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("photos")
data class Photo (
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo val alt: String,
    @ColumnInfo val url: String
)
package com.example.abl.players

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerKeys(
    @PrimaryKey val playerId: String,
    val previousKey: Int?,
    val nextKey: Int?
)

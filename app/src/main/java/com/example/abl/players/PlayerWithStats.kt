package com.example.abl.players

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerWithStats(
    @Embedded val player: Player,
    @Relation(
        parentColumn = "playerId",
        entityColumn = "playerId"
    )
    val stats: PlayerStats = PlayerStats(player.playerId)
)
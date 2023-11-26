package com.example.abl.leaders

import com.example.abl.players.Player

data class LeaderListItem(
    val itemId: Long,
    val player: Player,
    val statCategory: String,
    val statValue: String,
    val teamName: String
)
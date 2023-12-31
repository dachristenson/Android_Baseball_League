package com.example.abl.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abl.players.PlayerKeys

@Dao
interface PlayerKeysDao {
    @Query("SELECT * FROM playerKeys WHERE playerId = :playerId")
    suspend fun getPlayerKeysByPlayerId(playerId: String): PlayerKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<PlayerKeys>)

    @Query("DELETE FROM playerKeys")
    suspend fun deleteAllPlayerKeys()
}

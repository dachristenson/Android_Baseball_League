package com.example.abl.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.abl.standings.TeamStanding
import com.example.abl.scoreboard.ScheduledGame

@Dao
abstract class BaseballDao {
    @Insert
    abstract suspend fun insertStandings(standings: List<TeamStanding>)

    @Update
    abstract suspend fun updateStandings(standings: List<TeamStanding>)

    @Query("SELECT * FROM standings")
    abstract fun getStandings(): LiveData<List<TeamStanding>>

    @Query("SELECT * FROM standings")
    abstract suspend fun getCurrentStandings(): List<TeamStanding>
}
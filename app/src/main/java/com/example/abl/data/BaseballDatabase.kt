package com.example.abl.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.abl.players.Player
import com.example.abl.players.PlayerStats
import com.example.abl.scoreboard.ScheduledGame
import com.example.abl.standings.TeamStanding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [ScheduledGame::class,
        TeamStanding::class,
        Player::class,
        PlayerStats::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class BaseballDatabase : RoomDatabase() {
    abstract fun baseballDao(): BaseballDao
    companion object {
        @Volatile
        private var Instance : BaseballDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope):
                BaseballDatabase = Instance ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        BaseballDatabase::class.java,
                        "BaseballDatabase"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            scope.launch {
                                Instance
                                    ?.baseballDao()
                                    ?.insertStandings(TeamStanding.mockTeamStandings)
                            }
                        }
                    }).build()

            Instance = instance

            instance
        }
    }
}
package com.example.abl.settings

import android.content.SharedPreferences
import com.example.abl.R

val startingScreens = mapOf(
    "Leaders" to R.id.leadersFragment,
    "Players" to R.id.playersFragment,
    "Scoreboard" to R.id.scoreboardFragment,
    "Standings" to R.id.standingsFragment,
    "Teams" to R.id.teamsFragment
)

fun getSelectedStartingScreen(prefs: SharedPreferences) =
    prefs.getString(SettingsFragment.startingScreenPreferenceKey, null).let { startId ->
        startingScreens[startId] ?: R.id.scoreboardFragment
    }

package com.example.abl.settings

import android.os.Bundle
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.abl.R
import com.example.abl.teams.UITeam

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var favoriteTeamPreference: DropDownPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val ctx = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(ctx)

        this.favoriteTeamPreference = DropDownPreference(ctx).apply {
            key = favoriteTeamPreferenceKey
            title = getString(R.string.favorite_team)
            entries = (listOf("None")
                + UITeam.allTeams.map { it.teamName }).toTypedArray()
            entryValues = (listOf("")
                + UITeam.allTeams.map { it.teamId }).toTypedArray()
            setDefaultValue("")
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        }

        screen.addPreference(favoriteTeamPreference)

        favoriteTeamPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val teamId = newValue?.toString()

                setNavBarColorForTeam(teamId)

                if (teamId != null) {
                    favoriteTeamPreference.icon = getIconForTeam(teamId)
                }

                true
            }

        preferenceScreen = screen
    }

    override fun onBindPreferences() {
        favoriteTeamPreference.icon = getIconForTeam(favoriteTeamPreference.value)
    }

    private fun getIconForTeam(teamId: String) =
        UITeam.fromTeamId(teamId)?.let { team ->
            ContextCompat.getDrawable(requireContext(), team.logoId)
        }

    private fun setNavBarColorForTeam(teamId: String?) {
        val color = UITeam.getTeamPalette(requireContext(), teamId)
            ?.dominantSwatch
            ?.rgb
            ?: getDefaultColor()

        activity?.window?.navigationBarColor = color
    }

    private fun getDefaultColor(): Int {
        val colorValue = TypedValue()

        activity?.theme?.resolveAttribute(
            R.attr.colorPrimary,
            colorValue,
            true
        )

        return colorValue.data
    }

    companion object {
        const val favoriteTeamPreferenceKey = "favoriteTeam"
    }
}
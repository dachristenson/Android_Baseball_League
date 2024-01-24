package com.example.abl.notifications

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.abl.R

class ABLFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "Obtained new Firebase Token: [$token]")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        TODO()
    }

    private fun createNotificationConfig(
        ctx: Context,
        message: RemoteMessage
    ): NotificationConfig? =
        when (message.data["destination"]) {
            ctx.getString(R.string.notification_destination_player) ->
                NotificationConfig(
                    id = 10,
                    channel = ABLNotificationChannel.Players,
                    titleInput = message.data["playerName"] ?: "N/A",
                    textInput = message.data["playerName"] ?: "N/A",
                    smallIconId = R.drawable.ic_baseline_directions_run_24,
                    destinationId = R.id.singlePlayerFragment,
                    arguments = mapOf(
                        "playerId" to (message.data["playerId"] ?: ""),
                        "playerName" to (message.data["playerName"] ?: "")
                    )
                )

            ctx.getString(R.string.notification_destination_team) ->
                NotificationConfig(
                    id = 11,
                    channel = ABLNotificationChannel.Teams,
                    titleInput = message.data["teamName"] ?: "N/A",
                    textInput = message.data["teamName"] ?: "N/A",
                    smallIconId = R.drawable.ic_baseline_outlined_flag_24,
                    destinationId = R.id.singleTeamFragment,
                    arguments = mapOf(
                        "teamId" to (message.data["teamId"] ?: ""),
                        "teamName" to (message.data["teamName"] ?: "")
                    )
                )

            else -> null
        }

    companion object {
        private const val TAG = "ABLFirebaseMessagingService"
    }
}
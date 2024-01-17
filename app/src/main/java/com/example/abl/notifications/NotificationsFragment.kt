package com.example.abl.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abl.R
import dev.mfazio.abl.api.services.getDefaultABLService
import com.example.abl.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNotificationsBinding.inflate(inflater)

        val adapter = NotificationsAdapter().apply {
            submitList(notificationItems)
        }

        with(binding.notificationsList) {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        return binding.root
    }

    companion object {
        private val ablService = getDefaultABLService()
        private const val TAG = "NotificationsFragment"

        private val notificationItems = listOf<NotificationItem>(
            NotificationItem(
                1,
                "Local - Player",
                "Displays a local notification for Rolando Lopez, " +
                        "which when clicked, takes a user to his player page.",
                NotificationType.Local
            ) { ctx, _ ->
                val pendingIntent = NavDeepLinkBuilder(ctx)
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.singlePlayerFragment)
                    .setArguments(Bundle().apply {
                        putString("playerId", "lopezrol")
                        putString("playerName", "Rolando Lopez")
                    }).createPendingIntent()

                val channel = ABLNotificationChannel.Players

                val notification =
                    NotificationCompat.Builder(ctx, channel.channelId)
                        .setContentTitle(ctx.getString(R.string.lopez_notification_title))
                        .setContentText(ctx.getString(R.string.lopez_notification_text))
                        .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                NotificationManagerCompat.from(ctx).notify(
                    1, //This ID can be used to update/remove an existing notification
                    notification
                )
            }
        )
    }
}
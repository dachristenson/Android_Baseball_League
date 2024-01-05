package com.example.abl.settings

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.abl.R
import com.example.abl.databinding.FragmentAboutTheAppBinding

class AboutTheAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAboutTheAppBinding.inflate(inflater).apply {
            aboutTheAppImageCreditsButton.setOnClickListener {
                this@AboutTheAppFragment.findNavController().navigate(R.id.imageCreditsFragment)
            }

            listOf(
                aboutTheAppDescription,
                aboutTheAppImageCredits,
                aboutTheAppImagePlayerInfo
            ).forEach { it.movementMethod = LinkMovementMethod.getInstance() }
        }

        return binding.root

    }
}
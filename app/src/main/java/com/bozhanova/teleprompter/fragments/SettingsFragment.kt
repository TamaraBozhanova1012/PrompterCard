package com.bozhanova.teleprompter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bozhanova.teleprompter.R

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.settings_fragment, container, false)

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
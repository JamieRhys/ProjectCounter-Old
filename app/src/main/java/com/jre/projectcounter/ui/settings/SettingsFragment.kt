package com.jre.projectcounter.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.jre.projectcounter.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
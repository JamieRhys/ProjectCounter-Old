package com.jre.projectcounter.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.preference.PreferenceFragmentCompat
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.R

class SettingsFragment : PreferenceFragmentCompat() {
    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.nav_host)}

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.settings)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> navController.navigateUp()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clearOptionsMenu() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearOptionsMenu()
    }
}
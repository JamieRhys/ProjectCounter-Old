package com.jre.projectcounter.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.R
import com.jre.projectcounter.ui.shared.SharedViewModel

class SettingsFragment : PreferenceFragmentCompat() {
    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.nav_host) }

    // This allows us to be able to share our views and variables between each fragment.
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }

    private lateinit var addExampleProjectPreference: SwitchPreference
    private lateinit var deleteAllProjects: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setHasOptionsMenu(true)

        initUi()
    }

    private fun initUi() {
        addExampleProjectPreference = findPreference("pref_add_example_project")!!
        deleteAllProjects = findPreference("pref_delete_all_projects")!!

        addExampleProjectPreference.isChecked =
            sharedPreferences.getBoolean("pref_add_example_project", true)

        deleteAllProjects.setOnPreferenceClickListener {
            viewModel.deleteAllProjects()
            true
        }
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
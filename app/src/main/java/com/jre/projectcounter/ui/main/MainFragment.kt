package com.jre.projectcounter.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.PREF_RECYCLER_VIEW_GRID
import com.jre.projectcounter.PREF_RECYCLER_VIEW_LIST
import com.jre.projectcounter.R
import com.jre.projectcounter.data.entities.Project
import com.jre.projectcounter.databinding.FragmentMainBinding
import com.jre.projectcounter.ui.lifecycle.MainFragmentLifecycleObserver
import com.jre.projectcounter.ui.shared.SharedViewModel
import com.jre.projectcounter.utils.PrefsHelper

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), MainRecyclerAdapter.ProjectItemListener {
    // This allows us to be able to share our views and variables between each fragment.
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }
    /// Allows us to navigate our app in a clean and concise way.
    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.nav_host)}

    private lateinit var menuOptionList: MenuItem
    private lateinit var menuOptionGrid: MenuItem

    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        lifecycle.addObserver(MainFragmentLifecycleObserver())

        initRVLayoutManager()

        viewModel.projectData.observe(viewLifecycleOwner, {
            mainRecyclerAdapter = MainRecyclerAdapter(requireContext(), it, this)
            binding.projectRecyclerView.adapter = mainRecyclerAdapter
        })

        binding.mfFabAddProject.setOnClickListener { navController.navigate(R.id.addProjectFragment)}



        return binding.root

    }

    override fun onResume() {
        super.onResume()

        // Refreshes our recyclerView projectData if it's changed within the database.
        viewModel.refreshProjectData()

    }

    /** We implement and override the [MainRecyclerAdapter.ProjectItemListener] interface so we can
     *  get the item the user clicks.
     *  @param project The [Project] item which the user clicks on
     */
    override fun onProjectItemClick(project: Project) {
        Toast.makeText(
            requireContext(),
            "Project ${project.projectName} clicked.",
            Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        super.onCreateOptionsMenu(menu, inflater)

        menuOptionList = menu.findItem(R.id.recycler_view_toggle_list)
        menuOptionGrid = menu.findItem(R.id.recycler_view_toggle_grid)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        if(PrefsHelper.getItemType(
                requireContext(),
                MainRecyclerAdapter.PREF_MRA_RV_VIEW_KEY
            ) == MainRecyclerAdapter.PREF_MRA_RECYCLER_VIEW_LIST) {
            menu.findItem(R.id.recycler_view_toggle_list).isVisible = false
            menu.findItem(R.id.recycler_view_toggle_grid).isVisible = true


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId) {
            R.id.recycler_view_toggle_list -> {
                onOptionListViewClick()
            }
            R.id.recycler_view_toggle_grid -> {
                onOptionGridViewClick()
            }
            R.id.action_settings -> {
                onOptionSettingsClick()
            }
        }

        return true
    }

    /** Initialises our [RecyclerView] Layout Manager depending on whether the user tapped
     * the option for GridLayout or LinearLayout.
     */
    private fun initRVLayoutManager() {
        // Get the RecyclerView View key and if it matches Grid then assign our RecyclerView
        // LayoutManager to be GridLayoutManager otherwise set to LinearLayoutManager.
        if(PrefsHelper.getItemType(
                requireContext(),
                MainRecyclerAdapter.PREF_MRA_RV_VIEW_KEY
            ) == MainRecyclerAdapter.PREF_MRA_RECYCLER_VIEW_GRID) {
            binding.projectRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.projectRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /** What we need to do once our List View Menu Option is tapped by the user */
    private fun onOptionListViewClick() {
        // Sets our Preference to the List View so that the next time the user opens the app
        // We assign the correct layout manager.
        PrefsHelper.setItemType(
            requireContext(),
            MainRecyclerAdapter.PREF_MRA_RV_VIEW_KEY,
            MainRecyclerAdapter.PREF_MRA_RECYCLER_VIEW_LIST
        )

        menuOptionList.isVisible = false
        menuOptionGrid.isVisible = true

        binding.projectRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Reassign the adapter so it refreshes our RecyclerView.
        binding.projectRecyclerView.adapter = mainRecyclerAdapter
    }

    /** What we need to do once our Grid View Menu Option is tapped by the user. */
    private fun onOptionGridViewClick() {
        PrefsHelper.setItemType(
            requireContext(),
            MainRecyclerAdapter.PREF_MRA_RV_VIEW_KEY,
            MainRecyclerAdapter.PREF_MRA_RECYCLER_VIEW_GRID
        )

        menuOptionGrid.isVisible = false
        menuOptionList.isVisible = true

        binding.projectRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.projectRecyclerView.adapter = mainRecyclerAdapter
    }

    private fun onOptionSettingsClick() {
        navController.navigate(R.id.settingsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
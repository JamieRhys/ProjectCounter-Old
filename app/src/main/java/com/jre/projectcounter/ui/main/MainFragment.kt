package com.jre.projectcounter.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.R
import com.jre.projectcounter.data.entities.Project
import com.jre.projectcounter.databinding.FragmentMainBinding
import com.jre.projectcounter.ui.lifecycle.MainFragmentLifecycleObserver
import com.jre.projectcounter.ui.shared.SharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), MainRecyclerAdapter.ProjectItemListener {
    // This allows us to be able to share our views and variables between each fragment.
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }
    /// Allows us to navigate our app in a clean and concise way.
    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.nav_host)}

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        lifecycle.addObserver(MainFragmentLifecycleObserver())

        viewModel.projectData.observe(viewLifecycleOwner, {
            val adapter = MainRecyclerAdapter(requireContext(), it, this)
            binding.projectRecyclerView.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
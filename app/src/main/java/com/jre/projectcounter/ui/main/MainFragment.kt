package com.jre.projectcounter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.data.entities.Project
import com.jre.projectcounter.databinding.FragmentMainBinding
import com.jre.projectcounter.ui.shared.SharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(), MainRecyclerAdapter.ProjectItemListener {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel.projectData.observe(viewLifecycleOwner, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it, this)
            _binding!!.projectRecyclerView.adapter = adapter
        })

        return binding.root

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
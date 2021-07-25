package com.jre.projectcounter.ui.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.jre.projectcounter.CANCEL_DIALOG_FRAGMENT
import com.jre.projectcounter.R
import com.jre.projectcounter.data.entities.Project
import com.jre.projectcounter.databinding.FragmentAddProjectBinding
import com.jre.projectcounter.ui.dialogs.CancelDialogFragment
import com.jre.projectcounter.ui.shared.SharedViewModel

class AddProjectFragment : Fragment() {
    // Allows us to access our ViewBindings within the connected layout.
    private var _binding: FragmentAddProjectBinding? = null
    // Allows us to navigate the app in a clean and concise way.
    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.nav_host) }

    // Allows us to access variables anywhere within our app.
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }

    // This property is only valid between onCreateView() and onDestroyView()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        // Initialise our UI components so we can use them
        initUi()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_new_project)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initUi() {


        // Start with our Add button.
        binding.buttonAdd.setOnClickListener {
            // If the ProjectName EditText is not empty then we'll add it to our database so the user
            // is able to access the project later and start work on it.
            if(binding.etProjectName.text.toString().isNotEmpty()) {
                // Add the project to our database.
                viewModel.addProject(
                    Project(projectName = binding.etProjectName.text.toString())
                )

                // Navigate back to our MainFragment where we will display the newly created project
                navController.navigateUp()
            } else {
                binding.tilProjectName.error = "Enter Project Name"
            }
        }

        binding.buttonCancel.setOnClickListener {
            displayCancelDialogIfProjectNameNotEmptyOrNavUp()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> displayCancelDialogIfProjectNameNotEmptyOrNavUp()
        }

        return super.onOptionsItemSelected(item)
    }

    // Yes I know this is a long function name but it's descriptive no?
    private fun displayCancelDialogIfProjectNameNotEmptyOrNavUp() {
        if(binding.etProjectName.text.toString().isNotEmpty()) {
            CancelDialogFragment().show(childFragmentManager, CANCEL_DIALOG_FRAGMENT)
        } else {
            navController.navigateUp()
        }
    }

    private fun clearOptionsMenu() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearOptionsMenu()
        _binding = null
    }
}
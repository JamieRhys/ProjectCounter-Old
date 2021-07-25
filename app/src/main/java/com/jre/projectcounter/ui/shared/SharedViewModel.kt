package com.jre.projectcounter.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jre.projectcounter.data.entities.Project
import com.jre.projectcounter.data.repository.ProjectRepository

/** The [SharedViewModel] is associated with the MainFragment. */
class SharedViewModel(app: Application): AndroidViewModel(app) {
    // We initialise our repository
    private val projectRepo = ProjectRepository(app)
    // We have the MutableLiveData data passed through to this from our repository
    val projectData = projectRepo.projectData

    var newProject: Project? = null

    // This is the selected project which the user clicked on.
    val selectedProject = MutableLiveData<Project>()

    fun addProject(project: Project) {
        projectRepo.addProject(project)
    }

    fun refreshProjectData() {
        projectRepo.refreshProjectData()
    }

    fun deleteAllProjects() {
        projectRepo.deleteAllProjects()
    }
}
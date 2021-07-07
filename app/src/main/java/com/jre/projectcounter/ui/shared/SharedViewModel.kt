package com.jre.projectcounter.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jre.projectcounter.data.repository.ProjectRepository

class SharedViewModel(app: Application): AndroidViewModel(app) {
    private val projectRepo = ProjectRepository(app)

    val projectData = projectRepo.projectData
}
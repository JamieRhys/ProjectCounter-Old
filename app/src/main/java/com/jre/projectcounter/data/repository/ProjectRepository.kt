package com.jre.projectcounter.data.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.data.database.ProjectDatabase
import com.jre.projectcounter.data.entities.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectRepository(val app: Application) {
    val projectData = MutableLiveData<List<Project>>()

    private val projectDao = ProjectDatabase.getDatabase(app).projectDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data = projectDao.getAll()

            if(data.isEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Room Data is unusable", Toast.LENGTH_LONG).show()
                }

                Log.i(LOG_TAG, "Room data is unusable")
                getProjectData()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Room Data is usable", Toast.LENGTH_LONG).show()
                }

                Log.i(LOG_TAG, "Room data is usable")
                projectData.postValue(data)
            }
        }
    }

    private fun getProjectData() {
        // TODO: Get project data and publish it to the right place.
        projectData.postValue(emptyList())
    }
}
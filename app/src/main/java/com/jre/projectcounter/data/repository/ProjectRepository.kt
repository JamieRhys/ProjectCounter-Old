package com.jre.projectcounter.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.data.database.ProjectDatabase
import com.jre.projectcounter.data.entities.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** The repositories job is to encapsulate all of the logic needed to get our data. Decisions on
 *  where to get it from can be held within this class. The app can then ask the repository for the
 *  data without ever needing to know where it's come from.
 *  @param app The [Application] context
 */
class ProjectRepository(app: Application) {
    // The data we've collected is exposed through this MutableLiveData variable.
    val projectData = MutableLiveData<List<Project>>()

    // We get the dao object from our ProjectDatabase.
    private val projectDao = ProjectDatabase.getDatabase(app).projectDao()

    init {
        // This is done in the background IO thread to prevent the UI from being locked up
        // unnecessarily
        CoroutineScope(Dispatchers.IO).launch {
            // We grab the data from our database
            val data = projectDao.getAll()

            if(data.isEmpty()) {
                Log.i(LOG_TAG, "Database is empty. Creating example project.")
                // If the data contains an empty list, we should generate an example project.
                generateProjectData()
            } else {
                Log.i(LOG_TAG, "Database is not empty, passing data to main recycler.")
                // If the data doesn't contain an empty list, we should pass it to our main
                // recycler allowing that to display it.
                projectData.postValue(data)
            }
        }
    }

    /** Generates an example project and pushes it to our database and projectData. */
    private suspend fun generateProjectData() {
        Log.i(LOG_TAG, "Generating Example Project")
        val project = Project(projectName = "Example Project")
        projectDao.insertProject(project)
        projectData.postValue(projectDao.getAll())
    }
}
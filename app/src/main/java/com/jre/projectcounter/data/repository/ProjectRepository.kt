package com.jre.projectcounter.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jre.projectcounter.LOG_TAG
import com.jre.projectcounter.data.data.ProjectWithCounters
import com.jre.projectcounter.data.database.ProjectDatabase
import com.jre.projectcounter.data.entities.Counter
import com.jre.projectcounter.data.entities.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** The repositories job is to encapsulate all of the logic needed to get our data. Decisions on
 *  where to get it from can be held within this class. The app can then ask the repository for the
 *  data without ever needing to know where it's come from.
 *  @param app The [Application] context
 */
class ProjectRepository(app: Application) {
    // The data we've collected is exposed through this MutableLiveData variable.
    val projectData = MutableLiveData<List<Project>>()
    val projectsWithCounters = MutableLiveData<List<ProjectWithCounters>>()

    // We get the dao object from our ProjectDatabase.
    private val projectDao = ProjectDatabase.getDatabase(app).projectDao()

    private val counterDao = ProjectDatabase.getDatabase(app).counterDao()

    init {
        // This is done in the background IO thread to prevent the UI from being locked up
        // unnecessarily
        CoroutineScope(Dispatchers.IO).launch {
            // We grab the data from our database
            val data = getAll()

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

    /** Adds the project that's passed in to the database. */
    fun addProject(project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            projectDao.insertProject(project)
        }
    }

    fun deleteAllProjects() {
        CoroutineScope(Dispatchers.IO).launch {
            counterDao.deleteAll()
            projectDao.deleteAll()
        }
    }

    /** Refreshes [projectData] if the size of the database has changed since last refresh
     *  @return True if data had changed and false if not.
     */
    fun refreshProjectData() {
        CoroutineScope(Dispatchers.IO).launch {
            val projectDataSize = projectData.value?.size ?: 0

            if (getAll().size != projectDataSize) {
                Log.i(LOG_TAG, "Data has changed, refreshing main recyclerView")
                projectData.postValue(getAll())
            }
        }
    }

    /** Returns all elements within our database */
    private fun getAll() = projectDao.getAll()

    /** Generates an example project and pushes it to our database and projectData. */
    private suspend fun generateProjectData() {
        Log.i(LOG_TAG, "Generating Example Project")
        val project = Project(projectName = "Example Project")
        projectDao.insertProject(project)
        projectData.postValue(projectDao.getAll())

        var counter =
            projectDao.getAll()[0].projectId?.let {
                Counter(
                    counterName = "Counter 1",
                    projectId = it
                )
            }

        if (counter != null) {
            counterDao.insertCounter(counter)
        }

        counter =
                projectDao.getAll()[0].projectId?.let {
                    Counter(
                        counterName = "Counter 2",
                        projectId = it
                    )
                }

        if(counter != null) {
            counterDao.insertCounter(counter)
        }

        // TODO: Remove after successful:
        projectsWithCounters.postValue(projectDao.getProjectWithCounters())
    }
}
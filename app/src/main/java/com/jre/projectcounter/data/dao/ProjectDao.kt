package com.jre.projectcounter.data.dao

import androidx.room.*
import com.jre.projectcounter.data.data.ProjectWithCounters
import com.jre.projectcounter.data.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM table_projects")
    fun getAll(): List<Project>

    @Transaction
    @Query("SELECT * FROM table_projects")
    fun getProjectWithCounters(): List<ProjectWithCounters>

    @Insert
    suspend fun insertProject(project: Project)

    @Insert
    suspend fun insertProjects(projects: List<Project>)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("DELETE FROM table_projects")
    suspend fun deleteAll()
}
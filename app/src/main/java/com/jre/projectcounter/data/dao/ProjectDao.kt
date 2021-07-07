package com.jre.projectcounter.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jre.projectcounter.data.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM table_projects")
    fun getAll(): List<Project>

    @Insert
    suspend fun insertProject(project: Project)

    @Insert
    suspend fun insertProjects(projects: List<Project>)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("DELETE FROM table_projects")
    suspend fun deleteAll()
}
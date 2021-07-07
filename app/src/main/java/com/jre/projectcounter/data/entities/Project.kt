package com.jre.projectcounter.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Our Project Room Entity.
 * This has a one to many relationship in the database as there can be multiple comments attached.
 * @param id The ID number of the entity within our database. LEAVE BLANK as this is autogenerated.
 * @param projectName The name of the project
 */
@Entity(tableName = "table_projects")
class Project(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "project_name") var projectName: String
) {
}
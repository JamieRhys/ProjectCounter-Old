package com.jre.projectcounter.data.data

import androidx.room.Embedded
import androidx.room.Relation
import com.jre.projectcounter.data.entities.Counter
import com.jre.projectcounter.data.entities.Project

data class ProjectWithCounters(
    @Embedded val Project: Project,
    @Relation(
        parentColumn = "project_id",
        entityColumn = "project_id"
    ) val counters: List<Counter>
)

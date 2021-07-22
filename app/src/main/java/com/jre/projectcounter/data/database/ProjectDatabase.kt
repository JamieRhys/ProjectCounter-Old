package com.jre.projectcounter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jre.projectcounter.DB_NAME
import com.jre.projectcounter.data.dao.CounterDao
import com.jre.projectcounter.data.dao.ProjectDao
import com.jre.projectcounter.data.entities.Counter
import com.jre.projectcounter.data.entities.Project

@Database(entities = [Project::class, Counter::class], version = 3, exportSchema = false)
abstract class ProjectDatabase: RoomDatabase()  {
    abstract fun projectDao(): ProjectDao
    abstract fun counterDao(): CounterDao

    companion object {
        @Volatile var INSTANCE: ProjectDatabase? = null

        fun getDatabase(context: Context): ProjectDatabase {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return INSTANCE!!
        }
    }
}
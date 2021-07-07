package com.jre.projectcounter.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jre.projectcounter.R
import com.jre.projectcounter.data.entities.Project

/** The adapter that our main [RecyclerView] uses to display our [Project] items.
 * @param context The context our fragment is associated with
 * @param projects The [List] of [Project]s we need to display on the [RecyclerView]
 * @param itemListener The click listener which listens out for the [Project] item the user has clicked
 */
class MainRecyclerAdapter(
    private val context: Context,
    private val projects: List<Project>,
    private val itemListener: ProjectItemListener)
    : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    // The ViewHolder describes an item view and metadata about its position within a RecyclerView
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.pgi_tv_project_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.project_grid_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        with(holder) {
            nameText.text = project.projectName

            holder.itemView.setOnClickListener {
                itemListener.onProjectItemClick(project)
            }
        }
    }

    override fun getItemCount() = projects.size

    /** Implement a [ProjectItemListener] when you want to be able to deal with an item click. */
    interface ProjectItemListener {
        /** Allows you to deal with the [Project] item which the user clicks on
         * @param project The [Project] item which the user clicks on
         */
        fun onProjectItemClick(project: Project)
    }
}
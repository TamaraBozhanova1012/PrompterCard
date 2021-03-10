package com.bozhanova.teleprompter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bozhanova.teleprompter.R
import com.bozhanova.teleprompter.interfaces.CellClickListener
import com.bozhanova.teleprompter.models.Project

class ProjectsListAdapter (private val myDataSet: List<Project>,
                           private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<ProjectsListAdapter.ProjectViewHolder>(){

    class ProjectViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.projects_list_item, parent, false))
    {
        private var mTitleView: TextView? = null
        private var mYearView: TextView? = null


        init {
            mTitleView = itemView.findViewById(R.id.item_title)
            mYearView = itemView.findViewById(R.id.item_date)
        }

        fun bind(project: Project, clickListener: CellClickListener) {
            mTitleView?.text = project.title
            mYearView?.text = project.date
            itemView.setOnClickListener {
                clickListener.onItemClicked(project)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProjectViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project: Project = myDataSet[position]
        holder.bind(project, cellClickListener)
    }

    override fun getItemCount(): Int = myDataSet.size
}
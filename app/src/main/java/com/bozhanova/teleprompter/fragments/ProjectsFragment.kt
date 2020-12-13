package com.bozhanova.teleprompter.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bozhanova.teleprompter.R
import com.bozhanova.teleprompter.adapter.ProjectsListAdapter
import com.bozhanova.teleprompter.interfaces.CellClickListener
import com.bozhanova.teleprompter.models.Project
import kotlinx.android.synthetic.main.projects_fragment.*

class ProjectsFragment : Fragment(), CellClickListener {
    private val myProjects = listOf(
        Project("Project 8", "Oct.,23", "Нейтрофилы, или нейтрофильные гранулоциты, или нейтрофильные сегментоядерные гранулоциты, или полиморфонуклеа́рные нейрофи́лы, — наиболее многочисленная группа гранулоцитов, на долю которой приходится от 40 % до 70 % всех лейкоцитов у человека. Нейтрофилы являются частью врождённого иммунитета, их основная функция — фагоцитоз патогенных микроорганизмов (бактерий, грибков, простейших) и продуктов распада тканей организма. Нейтрофилы представляют собой очень подвижные клетки, которые проникают даже в те ткани, которые недоступны для других лейкоцитов. В зависимости от морфологии ядра нейтрофилы подразделяют на палочкоядерные (незрелые) и сегментоядерные (зрелые) нейтрофилы. Развитие нейтрофилов находится под контролем цитокинов, прежде всего G-CSF, а также GM-CSF, IL-3 и IL-6. В условиях воспалительного ответа количество нейтрофилов увеличивается под действием IL-17 и IL-23. Нейтрофилы являются основными фагоцитами кровотока, но в ходе острого воспаления интенсивно мигрируют в очаг воспаления. Они проникают через стенки кровеносных сосудов и движутся по градиенту различных провоспалительных молекул в ходе хемотаксиса. Нейтрофилы — самые многочисленные клетки, входящие в состав гноя, именно они придают ему беловатый или желтоватый цвет. Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 Project 8 "),
        Project("Project 7", "Oct.,22", "Project 7 Project 7 Project 7  Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 Project 7 "),
        Project("Project 6", "Oct.,21", "Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 Project 6 "),
        Project("Project 5","Oct.,20", "Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 Project 5 "),
        Project("Project 4", "Oct.,19", "Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 Project 4 "),
        Project("Project 3", "Oct.,19", "Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 Project 3 "),
        Project("Project 2", "Oct.,19", "Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 Project 2 "),
        Project("Project 1", "Oct.,19", "Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1 Project 1")

    )
    private lateinit var adapter: ProjectsListAdapter
    var listener: OnItemClickedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.projects_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projects_recycle_view.layoutManager = LinearLayoutManager(activity)
        adapter = ProjectsListAdapter(myProjects, this)
        projects_recycle_view.adapter = adapter

        DividerItemDecoration(activity, LinearLayoutManager(activity).orientation).apply {
            projects_recycle_view.addItemDecoration(this) }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnItemClickedListener
        if (listener == null) {
            return
        }
    }

    override fun onItemClicked(data: Project) {
        val bundle = bundleOf("project_title" to data.title, "project_scenario" to data.scenario)
        this.findNavController().navigate(R.id.action_projects_fragment_des_to_detail_projects_fragment_des, bundle)
        listener?.onItemSelected(data.scenario)
    }

    companion object {
        fun newInstance(): ProjectsFragment = ProjectsFragment()
    }
    interface OnItemClickedListener {
        fun onItemSelected(scenario : String)
    }
}
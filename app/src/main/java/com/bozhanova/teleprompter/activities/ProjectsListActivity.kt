package com.bozhanova.teleprompter.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bozhanova.teleprompter.R
import com.bozhanova.teleprompter.fragments.ProjectsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_projectslist.*

class ProjectsListActivity : AppCompatActivity(), ProjectsFragment.OnItemClickedListener{

    private var fabPlayIcon: Boolean = false
    lateinit var scenario: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projectslist)
        setupBottomNavMenu(findNavController(R.id.fragmentNavHost))
        projects_fab.setOnClickListener {
            onFabClick()
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }


    private fun onFabClick(){
        if(fabPlayIcon){
            bottomAppBar.visibility = View.GONE;
            projects_fab.hide();
            val bundle = bundleOf("project_scenario" to scenario)
            this.findNavController(R.id.fragmentNavHost).navigate(R.id.action_detail_projects_fragment_des_to_videoFragment, bundle)
        }
    }

    override fun onItemSelected(scenario : String) {
        projects_fab.setImageResource(R.drawable.play_arrow_36)
        fabPlayIcon = true;
        this.scenario = scenario
    }
}
package com.bozhanova.teleprompter.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bozhanova.teleprompter.R
import com.bozhanova.teleprompter.fragments.ProjectsFragment
import com.bozhanova.teleprompter.fragments.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_projectslist.*

class ProjectsListActivity : AppCompatActivity(), ProjectsFragment.OnItemClickedListener {

    private var fabPlayIcon: Boolean = false
    lateinit var scenario: String
    private val navController by lazy { findNavController(R.id.fragmentNavHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projectslist)
        
        projects_fab.setOnClickListener {
            onFabClick()
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            fabPlayIcon = when (destination.id) {
                in arrayOf(
                    R.id.projects_fragment_des,
                    R.id.settings_fragment_des
                ) -> {
                    projects_fab.setImageResource(R.drawable.add_white)
                    projects_fab.show()
                    bottomAppBar.visibility = View.VISIBLE
                    false
                }
                in arrayOf(
                    R.id.detail_projects_fragment_des
                ) -> {
                    projects_fab.setImageResource(R.drawable.play_arrow_36)
                    projects_fab.show()
                    bottomAppBar.visibility = View.VISIBLE
                    true
                }
                else -> {
                    bottomAppBar.visibility = View.GONE
                    projects_fab.hide()
                    false
                }
            }
        }
        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }


    private fun onFabClick(){
        if(fabPlayIcon){
            val bundle = bundleOf("project_scenario" to scenario)
            this.findNavController(R.id.fragmentNavHost).navigate(R.id.action_detail_projects_fragment_des_to_videoFragment, bundle)
        }
    }

    override fun onItemSelected(scenario : String) {
        this.scenario = scenario
    }

//    override fun onBackNavigate() {
//        projects_fab.show()
//        projects_fab.setImageResource(R.drawable.play_arrow_36)
//        fabPlayIcon = true;
//    }


}
package com.bozhanova.teleprompter.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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
        //val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
      //  bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
      //  openFragment(ProjectsFragment.newInstance())
        projects_fab.setOnClickListener {
            onFabClick()
        }
    }

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.menuHome -> {
//                val projectsFragment = ProjectsFragment.newInstance()
//                openFragment(projectsFragment)
//                projects_fab.setImageResource(R.drawable.add_white)
//                fabPlayIcon = false;
//                true
//            }
//            R.id.menuSettings -> {
//                val settingsFragment = SettingsFragment.newInstance()
//                openFragment(settingsFragment)
//                projects_fab.setImageResource(R.drawable.add_white)
//                fabPlayIcon = false;
//                true
//            }
//        }
//        false
//    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onFabClick(){
        if(fabPlayIcon){
//            val i = Intent(this, MainActivity::class.java)
//            i.putExtra("scenario", scenario)
//            startActivity(i)
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
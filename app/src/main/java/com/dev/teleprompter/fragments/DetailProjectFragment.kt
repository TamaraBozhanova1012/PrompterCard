package com.dev.teleprompter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev.teleprompter.R
import kotlinx.android.synthetic.main.detail_project_fragment.*

class DetailProjectFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.detail_project_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            val receivedTitle = bundle.getString("project_title", "")
            val receivedScenario = bundle.getString("project_scenario", "")// Key, default value
            screen_title.text = receivedTitle
            tv_title.text = receivedTitle
            project_scenario.text = receivedScenario
        }
    }

    companion object {
        fun newInstance(): DetailProjectFragment = DetailProjectFragment()
    }
}
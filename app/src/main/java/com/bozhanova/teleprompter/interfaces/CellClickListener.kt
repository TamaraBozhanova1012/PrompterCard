package com.bozhanova.teleprompter.interfaces

import com.bozhanova.teleprompter.models.Project

interface CellClickListener {
    fun onItemClicked(data: Project)
}
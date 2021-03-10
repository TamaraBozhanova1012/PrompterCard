package com.dev.teleprompter.interfaces

import com.dev.teleprompter.models.Project

interface CellClickListener {
    fun onItemClicked(data: Project)
}
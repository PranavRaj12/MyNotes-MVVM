package com.pranav.raj.mynotes.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pranav.raj.mynotes.data.TasksDao

class TasksViewModel @ViewModelInject constructor(
    private val tasksDao : TasksDao
) : ViewModel() {

    val tasks = tasksDao.getTasks().asLiveData()
}
package com.pranav.raj.mynotes.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pranav.raj.mynotes.data.PreferencesManager
import com.pranav.raj.mynotes.data.SortOrder
import com.pranav.raj.mynotes.data.TasksDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val tasksDao : TasksDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val preferencesFlow = preferencesManager.preferencesFlow

    private val taskFlow = combine(
        searchQuery,
        preferencesFlow
    ){
        query, filterPreferences ->
        Pair(query,filterPreferences)
    }.flatMapLatest {(query,filterPreferences)->
        tasksDao.getTasks(query,filterPreferences.sortOrder,filterPreferences.hideCompleted)
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch{
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompleted(hideCompleted : Boolean) = viewModelScope.launch {
        preferencesManager.hideCompleted(hideCompleted)
    }

    val tasks = taskFlow.asLiveData()
}


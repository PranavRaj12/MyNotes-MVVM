package com.pranav.raj.mynotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pranav.raj.mynotes.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class],version = 1)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun tasksDao() : TasksDao

    class Callback @Inject constructor(
       private val database: Provider<TaskDatabase>,
       @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().tasksDao()

            applicationScope.launch {

                dao.insert(Task("Learn kotlin"))
                dao.insert(Task("Learn MVVM architecture", important = true))
                dao.insert(Task("Android basics", completed = true))
            }

        }
    }
}
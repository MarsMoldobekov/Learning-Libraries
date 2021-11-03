package com.example.learninglibraries.domain.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learninglibraries.domain.database.dao.GithubUserDao
import com.example.learninglibraries.domain.database.dao.RepositoryDao
import com.example.learninglibraries.domain.database.entities.RoomGithubRepository
import com.example.learninglibraries.domain.database.entities.RoomGithubUser

@androidx.room.Database(entities = [RoomGithubUser::class, RoomGithubRepository::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val githubUserDao: GithubUserDao
    abstract val repositoryDao: RepositoryDao

    companion object {
        private const val DATABASE_NAME = "database.db"
        private var instance: Database? = null

        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please, call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, Database::class.java, DATABASE_NAME).build()
            }
        }
    }
}
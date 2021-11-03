package com.example.learninglibraries.domain.database.dao

import androidx.room.*
import com.example.learninglibraries.domain.database.entities.RoomGithubUser

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomGithubUser: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg roomGithubUser: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listOfRoomGithubUser: List<RoomGithubUser>)

    @Update
    fun update(roomGithubUser: RoomGithubUser)

    @Update
    fun update(vararg roomGithubUser: RoomGithubUser)

    @Update
    fun update(listOfRoomGithubUser: List<RoomGithubUser>)

    @Delete
    fun delete(roomGithubUser: RoomGithubUser)

    @Delete
    fun delete(vararg roomGithubUser: RoomGithubUser)

    @Delete
    fun delete(listOfRoomGithubUser: List<RoomGithubUser>)

    @Query("SELECT * FROM RoomGithubUser")
    fun getAll(): List<RoomGithubUser>

    @Query("SELECT * FROM RoomGithubUser WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): RoomGithubUser?
}
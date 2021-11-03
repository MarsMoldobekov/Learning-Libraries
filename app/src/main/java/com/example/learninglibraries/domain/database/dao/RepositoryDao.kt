package com.example.learninglibraries.domain.database.dao

import androidx.room.*
import com.example.learninglibraries.domain.database.entities.RoomGithubRepository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomGithubRepository: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg roomGithubRepository: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listOfRoomGithubRepository: List<RoomGithubRepository>)

    @Update
    fun update(roomGithubRepository: RoomGithubRepository)

    @Update
    fun update(vararg roomGithubRepository: RoomGithubRepository)

    @Update
    fun update(listOfRoomGithubRepository: List<RoomGithubRepository>)

    @Delete
    fun delete(roomGithubRepository: RoomGithubRepository)

    @Delete
    fun delete(vararg roomGithubRepository: RoomGithubRepository)

    @Delete
    fun delete(listOfRoomGithubRepository: List<RoomGithubRepository>)

    @Query("SELECT * FROM RoomGithubRepository")
    fun getAll(): List<RoomGithubRepository>

    @Query("SELECT * FROM RoomGithubRepository WHERE owner_id = :ownerId LIMIT 1")
    fun findByLogin(ownerId: String): RoomGithubRepository?
}
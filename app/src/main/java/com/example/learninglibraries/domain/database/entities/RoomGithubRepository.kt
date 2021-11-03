package com.example.learninglibraries.domain.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGithubRepository::class,
        parentColumns = ["id"],
        childColumns = ["owner_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomGithubRepository(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "full_name") var fullName: String,
    @ColumnInfo(name = "forks_count") var forksCount: Int,
    @ColumnInfo(name = "owner_id") var ownerId: String
)

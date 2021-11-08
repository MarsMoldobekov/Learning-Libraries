package com.example.learninglibraries.domain.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGithubUser::class,
        parentColumns = ["id"],
        childColumns = ["owner"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomGithubRepository(
    @PrimaryKey var id: Long?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "forks_count") var forksCount: Int?,
    @ColumnInfo(name = "full_name") var fullName: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "owner") var owner: Long?
)
package com.note.noteapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey @ColumnInfo(name = "id") val noteId: String,
    val title: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int = 7, // this will bee progress completed 0-100
    val imageUrl: String = ""
) {
    override fun toString() = title
}

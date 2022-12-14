package com.example.clean_mvvm_example.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(
    val title: String,
    val content: String,

    @ColumnInfo(name = "creation_data")
    var creationTime: Long,

    @ColumnInfo(name = "update_date")
    var updateTime: Long,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
){
    companion object{
        fun fromNote(note: Note) = NoteEntity(note.title, note.content, note.creationTime, note.updateTime, note.id)
    }
    fun toNote() = Note(title, content, creationTime, updateTime, id)
}

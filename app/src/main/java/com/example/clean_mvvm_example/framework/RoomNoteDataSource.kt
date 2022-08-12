package com.example.clean_mvvm_example.framework

import android.content.Context
import com.example.clean_mvvm_example.framework.db.DatabaseService
import com.example.clean_mvvm_example.framework.db.NoteEntity
import com.example.core.data.Note
import com.example.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context): NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()
    override suspend fun add(note: Note) =  noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))

}

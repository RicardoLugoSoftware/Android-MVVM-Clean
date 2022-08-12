package com.example.clean_mvvm_example.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.clean_mvvm_example.framework.di.ApplicationModule
import com.example.clean_mvvm_example.framework.di.DaggerViewModelComponet
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import com.example.core.usecase.AddNote
import com.example.core.usecase.GetAllNotes
import com.example.core.usecase.GetNote
import com.example.core.usecase.RemoveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val corutineScope = CoroutineScope(Dispatchers.IO)

    val repository =  NoteRepository(RoomNoteDataSource(application))

    @Inject
    lateinit var  useCases: UseCases

    init {
        DaggerViewModelComponet.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    fun saveNote(note:Note){
        corutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNote(id: Long) {
        corutineScope.launch {
            val note = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note){
        corutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }
}

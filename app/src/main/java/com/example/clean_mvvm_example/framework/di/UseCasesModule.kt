package com.example.clean_mvvm_example.framework.di

import com.example.clean_mvvm_example.framework.UseCases
import com.example.core.repository.NoteRepository
import com.example.core.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun providesUseCases(repository: NoteRepository) =
        UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount(),
        )
}

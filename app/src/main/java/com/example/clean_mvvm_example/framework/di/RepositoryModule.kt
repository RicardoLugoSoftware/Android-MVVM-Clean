package com.example.clean_mvvm_example.framework.di

import android.app.Application
import com.example.clean_mvvm_example.framework.RoomNoteDataSource
import com.example.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(app:Application) = NoteRepository(RoomNoteDataSource(app))
}

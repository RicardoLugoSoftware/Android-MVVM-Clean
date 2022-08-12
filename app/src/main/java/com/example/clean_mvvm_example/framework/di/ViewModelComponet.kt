package com.example.clean_mvvm_example.framework.di

import com.example.clean_mvvm_example.framework.ListViewModel
import com.example.clean_mvvm_example.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponet {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)

}

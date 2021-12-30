package dev.jmsg.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dev.jmsg.notes.model.entity.Note
import dev.jmsg.notes.model.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application){

    val noteRepository: NoteRepository

    init {
        noteRepository = NoteRepository(application)
    }

    fun getAllNotes():LiveData<List<Note>>{
        return noteRepository.getAllNotes().asLiveData()
    }

    fun insertNote(note: Note){
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note){
        noteRepository.deleteNote(note)
    }

    fun deleteSomeNotes(vararg note:Note){
        noteRepository.deleteSomeNotes(*note)
    }

    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
    }
}
package dev.jmsg.notes.model.repository

import android.content.Context
import dev.jmsg.notes.model.dao.NoteDao
import dev.jmsg.notes.model.database.NoteDatabase
import dev.jmsg.notes.model.entity.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(context: Context) {

    val noteDao:NoteDao
    lateinit var liveNotes: Flow<List<Note>>
    init {
        val db : NoteDatabase = NoteDatabase.getDatabase(context)
        noteDao = db.getDao()
    }

    fun getAllNotes():Flow<List<Note>>{
        liveNotes = noteDao.getNotes()
        return liveNotes
    }

    fun insertNote(note: Note){
        Thread{
            noteDao.insertNote(note)
        }.start()
    }

    fun deleteNote(note: Note){
        Thread{
            noteDao.deleteSomeNotes(note)
        }.start()
    }

    fun deleteSomeNotes(vararg note:Note){
        Thread{
            noteDao.deleteSomeNotes(*note)
        }.start()
    }

    fun updateNote(note: Note) {
        Thread{
            noteDao.updateNote(note)
        }.start()
    }
}
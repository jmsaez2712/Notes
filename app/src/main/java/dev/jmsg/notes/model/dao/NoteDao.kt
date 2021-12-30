package dev.jmsg.notes.model.dao

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import dev.jmsg.notes.model.entity.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = CASCADE)
    fun insertNote(note: Note):Long

    @Query("SELECT * FROM notes ORDER BY date(date) DESC")
    fun getNotes(): Flow<List<Note>>

    @Update
    fun updateNote(note: Note): Int

    @Delete
    fun deleteNote(note: Note):Int

    @Delete
    fun deleteSomeNotes(vararg note:Note): Int
}
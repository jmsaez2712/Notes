package dev.jmsg.notes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import dev.jmsg.notes.model.entity.Note
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.jmsg.notes.model.dao.CategoryDao
import dev.jmsg.notes.model.dao.NoteDao
import dev.jmsg.notes.model.entity.Category

@Database(entities = [Note::class, Category::class], version = 3)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao():NoteDao
    abstract fun getCategoryDao():CategoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE categories (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT)")
                database.execSQL("ALTER TABLE notes ADD COLUMN `category` INTEGER")
            }
        }

        fun getDatabase(context: Context): NoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2).build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
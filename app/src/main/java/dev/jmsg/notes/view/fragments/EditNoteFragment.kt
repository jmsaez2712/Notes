package dev.jmsg.notes.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dev.jmsg.notes.R
import dev.jmsg.notes.model.entity.Note
import dev.jmsg.notes.viewmodel.NoteViewModel
import java.lang.Exception

class EditNoteFragment : Fragment() {

    //Define a variable to obtain the local date
    @RequiresApi(Build.VERSION_CODES.O)
    var date = java.time.LocalDate.now()

    //Define the rest of the variable needed
    lateinit var nvm: NoteViewModel
    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    lateinit var note: Note
    var bundle: Bundle? = null
    lateinit var toolbar: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComp(view)
        setNavigation(view)
        try {
            bundle = arguments
            note = bundle?.getParcelable<Note>("note")!!
            etTitle.setText(note.title)
            etContent.setText(note.content)
            toolbar.setTitle("Edit Note")
        } catch (e : Exception){}
    }

    private fun initComp(view :View) {
        etTitle = view.findViewById(R.id.etEditTitle)
        etContent = view.findViewById(R.id.etEditContent)
        toolbar = view.findViewById(R.id.toolbarEditNote)
        nvm = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
    }

    private fun setNavigation(view : View){
        var navController = Navigation.findNavController(view)
        var appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        toolbar.inflateMenu(R.menu.note_add_menu)
        toolbar.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        NavigationUI.setupWithNavController(toolbar, navController,appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when(id){
            R.id.add_note-> updateNote(note = createNote())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkNote(note :Note):Boolean{
        return !note.title?.isEmpty()!! || !note.content?.isEmpty()!!
    }

    private fun createNote(): Note{
        var updatedNote = Note(note.id,etTitle.text.toString(),etContent.text.toString(), date.toString())
        return updatedNote
    }

    private fun updateNote(note: Note){
        if(checkNote(note)) {
            nvm.updateNote(note)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }



}
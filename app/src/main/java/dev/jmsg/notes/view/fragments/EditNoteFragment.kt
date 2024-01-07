package dev.jmsg.notes.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.textfield.TextInputLayout
import dev.jmsg.notes.R
import dev.jmsg.notes.model.entity.Category
import dev.jmsg.notes.model.entity.Note
import dev.jmsg.notes.viewmodel.CategoryViewModel
import dev.jmsg.notes.viewmodel.NoteViewModel


class EditNoteFragment : Fragment() {

    //Define a variable to obtain the local date
    @RequiresApi(Build.VERSION_CODES.O)
    var date = java.time.LocalDate.now()

    //Define the rest of the variable needed
    lateinit var nvm: NoteViewModel
    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    lateinit var note: Note
    lateinit var category: Category
    var bundle: Bundle? = null
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var cvm: CategoryViewModel
    var firstType: Boolean = true
    lateinit var categoryAutoComplete: AutoCompleteTextView
    lateinit var categoryExposedDropdown: TextInputLayout


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

    @RequiresApi(Build.VERSION_CODES.O)
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
        cvm = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        categoryAutoComplete = view.findViewById(R.id.categoryAutoComplete)
        categoryExposedDropdown = view.findViewById(R.id.categoryDropdown)

        categoryAutoComplete.onItemSelectedListener
        loadSpinner()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNavigation(view : View){
        var navController = Navigation.findNavController(view)
        var appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        toolbar.inflateMenu(R.menu.note_add_menu)
        toolbar.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        NavigationUI.setupWithNavController(toolbar, navController,appBarConfiguration)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when(id){
            R.id.add_note-> updateNote(createNote())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkNote(note :Note):Boolean{
        return !note.title?.isEmpty()!! || !note.content?.isEmpty()!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNote(): Note{
        Log.d(":::CATEGORY", category.toString())

        Log.d(":::CATEGORY", category.toString())
        var noteCategory = if (category!!.id != 0 || category != null) category!!.id else 0

        var updatedNote = Note(note.id,etTitle.text.toString(),etContent.text.toString(), date.toString(), noteCategory)
        return updatedNote
    }

    private fun updateNote(note: Note){
        if(checkNote(note)) {
            nvm.updateNote(note)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    private fun loadSpinner() {
        cvm.getCategories().observe(viewLifecycleOwner) { types ->
            if (firstType) {
                var types = types.toMutableList()
                val type = Category(0, "Category")
                types.add(0, type)
                this.firstType = false
            }
            val adapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
                this.requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                types
            )
            this.categoryAutoComplete.setAdapter(adapter)
            this.categoryAutoComplete.onItemClickListener =
                OnItemClickListener { parent, arg1, position, arg3 ->
                    val item = parent.getItemAtPosition(position)
                    if (item is Category) {
                        category = item
                    }
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                types.forEach { t ->
                    if (t.id === note.category) {
                        val pos: Int = types.indexOf(t)
                        categoryAutoComplete.setSelection(pos)
                    }
                }
            } catch (e: Exception) {
            }
        }
        }
    }


}
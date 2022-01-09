package dev.jmsg.notes.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.jmsg.notes.R
import dev.jmsg.notes.model.entity.Note
import dev.jmsg.notes.view.adapter.NoteAdapter
import dev.jmsg.notes.viewmodel.NoteViewModel


import android.view.*
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.card.MaterialCardView
import dev.jmsg.notes.view.viewholder.LookupClass


class MainFragment : Fragment() {

    lateinit var nvm: NoteViewModel
    var fab : FloatingActionButton? = null
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var actionMode : ActionMode? = null
    lateinit var rvNote : RecyclerView
    lateinit var noteAdapter: NoteAdapter
    lateinit var tracker: SelectionTracker<Long>
    lateinit var collapsingToolbar: CollapsingToolbarLayout
    lateinit var appbar: AppBarLayout
    var selected = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvNote = view.findViewById(R.id.rvNotes)
        rvNote.setLayoutManager(StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL))
        nvm = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        noteAdapter = NoteAdapter(context)
        rvNote.adapter = noteAdapter

        var listNotes : LiveData<List<Note>> = nvm.getAllNotes()
        listNotes.observe(viewLifecycleOwner, Observer { notes:List<Note> ->
            noteAdapter.setCarList(notes)
        })

        tracker = SelectionTracker.Builder<Long>(
            "selection-1",
            rvNote,
            StableIdKeyProvider(rvNote),
            LookupClass(rvNote),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        noteAdapter.setTracker(tracker)

        tracker?.addObserver(
            object: SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    val nItems:Int? = tracker?.selection?.size()
                    if (tracker.hasSelection()) {
                        if (actionMode == null) {
                            actionMode = view.startActionMode(actionModeCallback)
                        }
                        updateContextualActionBarTitle()
                    } else {
                        actionMode?.finish()
                    }
                }
            })



        toolbar = view.findViewById(R.id.toolbar)
        collapsingToolbar = view.findViewById(R.id.collapsing);
        appbar = view.findViewById(R.id.appbar)

        var navController = Navigation.findNavController(view)
        var appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupWithNavController(toolbar, navController,appBarConfiguration)

        fab = giveFab()
        fab?.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = "Notes"
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })


    }


    fun giveFab():FloatingActionButton?{
        var fabCool = view?.findViewById<FloatingActionButton>(R.id.fabMain)
        return fabCool
    }


    val actionModeCallback = object: ActionMode.Callback{
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.note_cab_menu, menu)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
            when(menu?.itemId){
               R.id.delete_option->{
                   noteAdapter.deleteNote()
                   tracker.clearSelection()
                   mode?.finish()
                   return true
               }
            }
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) {
            tracker.clearSelection()
            actionMode = null
        }

    }

    private fun updateContextualActionBarTitle() {
        actionMode?.title = "${tracker.selection.size()} note(s) selected"
    }



}
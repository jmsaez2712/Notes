package dev.jmsg.notes.view.viewholder

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import dev.jmsg.notes.R
import dev.jmsg.notes.model.entity.Note
import dev.jmsg.notes.viewmodel.NoteViewModel


@SuppressLint("ResourceAsColor")
@RequiresApi(Build.VERSION_CODES.M)
class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
    val tvContent:TextView = itemView.findViewById(R.id.tvContent)
    var note: Note? = null
    var actionMode : ActionMode? = null
    var some = itemView.context.applicationContext
    var card = itemView.findViewById<MaterialCardView>(R.id.cardView)
    var selectedCards: ArrayList<Note> = ArrayList()


    init {

        /*var actionModeCallback: ActionMode.Callback = object: ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.note_menu,menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                var id = item?.itemId

                if(card.isSelected){
                    selectedCards.add(note!!)
                    Log.v(":::X",note?.title!!)
                }

                when(id){
                    R.id.delete_option -> {
                        var noteViewModel = NoteViewModel(some as Application)
                        noteViewModel.deleteNote(note!!)
                        mode?.finish()
                        card.setChecked(false)
                        return true
                    }

                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
                card.setChecked(false)
            }
        }*/

        card.setOnClickListener{

                var bundle = Bundle()
                bundle.putParcelable("note", note)
                //Log.v("::XXX", note?.title!!)
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_mainFragment_to_editNoteFragment, bundle)


        }

        /*card.setOnLongClickListener{
            actionMode = itemView.startActionMode(actionModeCallback)
            true
        }*/

    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object: ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition

            override fun getSelectionKey(): Long? = itemId

            // More code here

        }



}
package dev.jmsg.notes.view.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.jmsg.notes.R
import dev.jmsg.notes.model.entity.Note
import dev.jmsg.notes.view.viewholder.NoteViewHolder
import dev.jmsg.notes.viewmodel.NoteViewModel


class NoteAdapter(context: Context?) : RecyclerView.Adapter<NoteViewHolder>() {
    var listNote: List<Note>? = null;
    var selected = ArrayList<Note>()
    private var tracker: SelectionTracker<Long>? = null
    var noteViewModel: NoteViewModel? = null
    val context = context
    init {
        setHasStableIds(true)
        noteViewModel = NoteViewModel(context?.applicationContext as Application)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var note: Note? = listNote?.get(position)
        holder.note = note
        holder.tvTitle.setText(note?.title)
        holder.tvContent.setText(note?.content)

        if(tracker!!.isSelected(position.toLong())) {
            holder.card.setChecked(true)
            holder.card.setCardBackgroundColor(context!!.getColor(R.color.primaryDarkColor));
            selected.add(note!!)
        } else {
            holder.card.setChecked(false)
            val ui = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
            when(ui){
                Configuration.UI_MODE_NIGHT_NO->{
                    holder.card.setCardBackgroundColor(context!!.getColor(R.color.primaryLightColor));
                }

                Configuration.UI_MODE_NIGHT_YES->{
                    holder.card.setCardBackgroundColor(context!!.getColor(R.color.Dark_primaryLightColor));
                }
            }
//
            selected.remove(note!!)
        }

    }

    override fun getItemCount(): Int {
        return listNote?.size ?: 0
    }

    fun setCarList(noteNewList : List<Note> ){
        this.listNote = noteNewList;
        notifyDataSetChanged();
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

    fun deleteNote(){
       selected.forEach {
          noteViewModel?.deleteNote(it)
       }
    }



}
package dev.jmsg.notes.view.viewholder

import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class LookupClass (private val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if(view != null) {
            return (recyclerView.getChildViewHolder(view) as NoteViewHolder).getItemDetails()
        }
        return null
    }
}
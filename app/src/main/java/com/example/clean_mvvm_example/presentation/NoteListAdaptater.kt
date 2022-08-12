package com.example.clean_mvvm_example.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clean_mvvm_example.R
import com.example.core.data.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteListAdaptater(var notes : ArrayList<Note>, val actions: ListAction): RecyclerView.Adapter<NoteListAdaptater.NoteViewHolder>() {
    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val layout = view.noteLayout
        private val noteTitle = view.title
        private val noteContent = view.content
        private val noteDate = view.date
        private val noteWords =  view.wordCount

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content

            val sdf = SimpleDateFormat("dd MM, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last updated: ${sdf.format(resultDate)}"

            layout.setOnClickListener{
                actions.OnClick(note.id)
            }

            noteWords.text = "Words: ${note.wordCount}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    fun updateNotes(newNotes:  List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}

package com.example.clean_mvvm_example.presentation

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.clean_mvvm_example.R
import com.example.clean_mvvm_example.databinding.FragmentListBinding
import com.example.clean_mvvm_example.databinding.FragmentNoteBinding
import com.example.clean_mvvm_example.framework.NoteViewModel
import com.example.core.data.Note
import kotlinx.android.synthetic.main.item_note.*

class NoteFragment : Fragment() {

    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private var currenNote = Note("","",0L,0L)
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeViewModel(){
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                hideKeyboard()
                Navigation.findNavController(binding.titleView).popBackStack()
            }else Toast.makeText(context, "Somenthing went wrong please try again", Toast.LENGTH_LONG).show()
        })

        viewModel.currentNote.observe(viewLifecycleOwner, {
            it?.let{
                currenNote = it
                binding.titleView.setText(it.title, TextView.BufferType.EDITABLE)
                binding.contentView.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if(noteId != 0L){
            viewModel.getNote(noteId)
        }
        binding.checkButton.setOnClickListener {
            if(binding.titleView.text.toString() != "" || binding.contentView.text.toString() != ""){
                val time = System.currentTimeMillis()
                currenNote.title = binding.titleView.text.toString()
                currenNote.content = binding.contentView.text.toString()
                currenNote.updateTime = time
                if(currenNote.id == 0L) currenNote.creationTime = time
                viewModel.saveNote(currenNote)
            }
        }
        observeViewModel()
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.titleView.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteNote -> {
                if(context != null && noteId != 0L) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes") { dialogInterface, i -> viewModel.deleteNote(currenNote) }
                        .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
                        .create()
                        .show()
                }
            }
        }
        return true
    }
}

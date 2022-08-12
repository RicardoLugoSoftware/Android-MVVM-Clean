package com.example.clean_mvvm_example.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clean_mvvm_example.R
import com.example.clean_mvvm_example.databinding.FragmentListBinding
import com.example.clean_mvvm_example.framework.ListViewModel

class ListFragment : Fragment(), ListAction {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val noteListAdaptater = NoteListAdaptater(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdaptater
        }
        binding.addNote.setOnClickListener { goToNoteDetail() }
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner, { notesList ->
            binding.loadingView.visibility = View.GONE
            binding.notesListView.visibility = View.VISIBLE
            noteListAdaptater.updateNotes(notesList.sortedBy { it.updateTime })
        })
    }

    private fun goToNoteDetail(id:Long = 0L){
        val action: NavDirections = ListFragmentDirections.actionGoToNote(id)
        Navigation.findNavController(this.requireView()).navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    override fun OnClick(id: Long) {
        goToNoteDetail(id)
    }
}

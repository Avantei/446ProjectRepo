package com.example.mealplanner.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentGroupsBinding

class GroupsFragment : Fragment() {

    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve viewModel from parent activity
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        val root = binding.root

        val recyclerView = binding.recyclerView
        val recycleAdaptor = GroupListAdaptor()

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            recycleAdaptor.submitList(it.toList())
        })

        recyclerView.adapter = recycleAdaptor


        val inputName = binding.inputName
        binding.addButton.setOnClickListener {
            viewModel.addGroup(inputName.text.toString())
        }
//        val textView: TextView = binding.warning
//        viewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
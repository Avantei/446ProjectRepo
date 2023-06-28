package com.example.mealplanner.ui.userActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentGroupsBinding
import com.example.mealplanner.ui.eventDetailActivity.EventActivity
import com.example.mealplanner.ui.groupActivity.GroupActivity

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
    ): View {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        val root = binding.root

        val recyclerView = binding.recyclerView
        val recycleAdaptor = GroupListAdaptor {
            Log.d("TODO", "Clicked Group ${it.name}")
            // TODO: go to a group page here
            val intent = Intent(requireActivity(), GroupActivity::class.java)
            intent.putExtra("groupName", it.name)
            startActivity(intent)
        }

        viewModel.groups.observe(viewLifecycleOwner) {
            recycleAdaptor.submitList(it.toList())
        }

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
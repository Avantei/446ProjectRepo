package com.example.mealplanner.ui.groupActivity.members

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentGroupMembersBinding
import com.example.mealplanner.ui.groupActivity.events.EventListAdaptor

class MembersFragment : Fragment() {

    private var _binding: FragmentGroupMembersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val membersViewModel =
            ViewModelProvider(this).get(MembersViewModel::class.java)

        _binding = FragmentGroupMembersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        val recyclerAdaptor = MemberListAdaptor {
            Log.d("TODO", "MEMBER CLICKED - NAME: $it")
        }

        membersViewModel.groupMembers.observe(viewLifecycleOwner) {
            recyclerAdaptor.submitList(it.toList())
        }

        recyclerView.adapter = recyclerAdaptor

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
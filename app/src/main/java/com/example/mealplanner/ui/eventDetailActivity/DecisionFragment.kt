package com.example.mealplanner.ui.eventDetailActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentDecisionBinding
import com.example.mealplanner.databinding.FragmentGroupsBinding
import com.example.mealplanner.ui.user.UserViewModel

// chatGPT was used for parts of this file

class DecisionFragment : Fragment() {
    private lateinit var binding: FragmentDecisionBinding
    lateinit var restaurantName: String
    var restaurantImageResourceId: Int = 0
    private lateinit var viewModel: EventDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EventDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDecisionBinding.inflate(inflater, container, false)

        val root = binding.root

        viewModel.decisionName.observe(viewLifecycleOwner) {
            binding.textRestaurant.text = it
        }
        viewModel.decisionImageId.observe(viewLifecycleOwner) {
            binding.imageRestaurant.setImageResource(it)
        }
        return root
    }

}
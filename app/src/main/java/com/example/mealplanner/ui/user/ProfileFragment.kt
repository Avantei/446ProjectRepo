package com.example.mealplanner.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
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

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root = binding.root

        viewModel.username.observe(viewLifecycleOwner) {
            binding.nameText.setText(it)
        }
        viewModel.isVegetarian.observe(viewLifecycleOwner){binding.vegetarianBtn.isChecked = it}
        viewModel.isVegan.observe(viewLifecycleOwner){binding.veganBtn.isChecked = it}
        viewModel.isGlutenFree.observe(viewLifecycleOwner){binding.glutenBtn.isChecked = it}
        viewModel.isDairyFree.observe(viewLifecycleOwner){binding.dairyBtn.isChecked = it}
        viewModel.isHalal.observe(viewLifecycleOwner){binding.halalBtn.isChecked = it}
        viewModel.isKosher.observe(viewLifecycleOwner){binding.kosherBtn.isChecked = it}
        // TODO: add listener for toggling dietary
        // TODO: add preference
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
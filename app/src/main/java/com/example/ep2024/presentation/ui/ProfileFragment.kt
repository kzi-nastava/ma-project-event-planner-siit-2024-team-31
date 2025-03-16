package com.example.ep2024.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ep2024.MainActivity
import com.example.ep2024.R
import com.example.ep2024.databinding.FragmentProfileBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRole: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        userRole = (activity as MainActivity).userRole

        binding.textViewUserRole.text = "Роль: $userRole"

        // TODO: DEPENDS ON ROLE !!!

        binding.buttonLogout.setOnClickListener {
            (activity as MainActivity).logoutUser()
            findNavController().navigate(R.id.mainPageFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.ep2024.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ep2024.databinding.FragmentServiceProductInfoBinding
import com.example.ep2024.domain.model.Service

class ServiceProductInfoFragment : Fragment() {

    private var _binding: FragmentServiceProductInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var service: Service

    companion object {
        private const val ARG_SERVICE = "arg_service"

        fun newInstance(service: Service) = ServiceProductInfoFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentServiceProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewServiceName.text = service.name
        binding.textViewServiceDescription.text = service.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
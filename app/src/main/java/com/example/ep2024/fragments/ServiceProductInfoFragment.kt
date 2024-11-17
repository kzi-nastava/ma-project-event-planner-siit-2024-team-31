package com.example.ep2024.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ep2024.databinding.FragmentServiceProductInfoBinding
import com.example.ep2024.models.Service

class ServiceProductInfoFragment : Fragment() {

    private var _binding: FragmentServiceProductInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var service: Service

    companion object {
        private const val ARG_SERVICE = "arg_service"

        fun newInstance(service: Service) = ServiceProductInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_SERVICE, service)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            service = it.getParcelable(ARG_SERVICE)!!
        }
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
        binding.textViewServiceCharacteristics.text = service.characteristics
        binding.textViewServicePrice.text = "$${service.price}"
        binding.textViewServiceDiscount.text = "${service.discount}%"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
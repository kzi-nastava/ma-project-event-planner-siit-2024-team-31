package com.example.ep2024.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ep2024.databinding.FragmentEventInfoBinding
import com.example.ep2024.domain.model.Event

class EventInfoFragment : Fragment() {

    private var _binding: FragmentEventInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var event: Event

    companion object {
        private const val ARG_EVENT = "arg_event"

        fun newInstance(event: Event) = EventInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_EVENT, event)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getParcelable(ARG_EVENT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewEventName.text = event.name
        binding.textViewEventDescription.text = event.description
        binding.textViewEventCity.text = event.city
        binding.textViewEventLocation.text = event.location
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
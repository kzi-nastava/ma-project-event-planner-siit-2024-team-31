package com.example.ep2024.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ep2024.R
import com.example.ep2024.adapters.ServicesAdapter
import com.example.ep2024.databinding.FragmentMyServicesBinding
import com.example.ep2024.models.Service
import com.example.ep2024.utils.service.ConfirmationMethod

class MyServicesFragment : Fragment() {

    private var _binding: FragmentMyServicesBinding? = null
    private val binding get() = _binding!!

    private lateinit var servicesAdapter: ServicesAdapter
    private val serviceList = mutableListOf<Service>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        servicesAdapter = ServicesAdapter(serviceList)
        binding.recyclerViewServices.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = servicesAdapter
        }

        loadServices()

        binding.editTextSearchServices.addTextChangedListener { text ->
            val query = text.toString()
            searchServices(query)
        }

        binding.buttonFilterServices.setOnClickListener {
            //TODO: Implement Filtering
            Toast.makeText(context, "Filter functionality not implemented yet.", Toast.LENGTH_SHORT).show()
        }

        binding.buttonAddService.setOnClickListener {
            findNavController().navigate(R.id.action_myServicesFragment_to_createServiceFragment)
        }

        servicesAdapter.setOnItemClickListener { service ->
            val fragment = ServiceProductInfoFragment.newInstance(service)
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }

        // Listen for the result from CreateServiceFragment
        parentFragmentManager.setFragmentResultListener("newServiceRequestKey", this) { _, bundle ->
            val newService = bundle.getParcelable<Service>("newServiceKey")
            newService?.let {
                addService(it)
            }
        }
    }

    private fun loadServices() {
        //TODO:  Fetch services from the server
        // For demonstration, using dummy data
        serviceList.addAll(getDummyServices())
        servicesAdapter.notifyDataSetChanged()
    }

    private fun getDummyServices(): List<Service> {
        return listOf(
            Service(
                id = "1",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "1",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC,
            )
        )
    }

    private fun searchServices(query: String) {
        //TODO: Implement search logic
        val filteredList = serviceList.filter { it.name.contains(query, true) }
        servicesAdapter.updateList(filteredList)
    }

    private fun addService(service: Service) {
        serviceList.add(service)
        servicesAdapter.notifyItemInserted(serviceList.size - 1)
        binding.recyclerViewServices.scrollToPosition(serviceList.size - 1)
        Toast.makeText(context, "Service added successfully!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
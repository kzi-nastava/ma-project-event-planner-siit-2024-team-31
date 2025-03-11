package com.example.ep2024.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ep2024.R
import com.example.ep2024.databinding.FragmentMainPageBinding
import com.example.ep2024.domain.model.Event
import com.example.ep2024.domain.model.Service
import com.example.ep2024.presentation.adapter.EventAdapter
import com.example.ep2024.presentation.adapter.ServicesAdapter
import com.example.ep2024.domain.model.service.ConfirmationMethod

class MainPageFragment : Fragment() {

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var topEventsAdapter: EventAdapter
    private lateinit var allEventsAdapter: EventAdapter
    private lateinit var topServicesAdapter: ServicesAdapter
    private lateinit var allServicesAdapter: ServicesAdapter

    private val topEventsList = mutableListOf<Event>()
    private val allEventsList = mutableListOf<Event>()
    private val topServicesList = mutableListOf<Service>()
    private val allServicesList = mutableListOf<Service>()

    private var allEventsLoaded = false
    private var allServicesLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerViews()

        loadTopEvents()
        loadAllEvents()
        loadTopServices()
        loadAllServices()

        setupSearchFunctionality()

        handleItemClicks()

        binding.buttonLoadMoreEvents.setOnClickListener {
            allEventsLoaded = true
            loadAllEvents()
            binding.recyclerViewAllEvents.isNestedScrollingEnabled = true
            binding.buttonLoadMoreEvents.visibility = View.GONE
        }

        binding.buttonLoadMoreServicesProducts.setOnClickListener {
            allServicesLoaded = true
            loadAllServices()
            binding.recyclerViewAllServices.isNestedScrollingEnabled = true
            binding.buttonLoadMoreServicesProducts.visibility = View.GONE
        }
    }

    private fun initRecyclerViews() {
        topEventsAdapter = EventAdapter(topEventsList)
        binding.recyclerViewTopEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topEventsAdapter
        }

        allEventsAdapter = EventAdapter(allEventsList)
        binding.recyclerViewAllEvents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allEventsAdapter
            isNestedScrollingEnabled = false
        }

        topServicesAdapter = ServicesAdapter(topServicesList)
        binding.recyclerViewTopServices.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topServicesAdapter
        }

        allServicesAdapter = ServicesAdapter(allServicesList)
        binding.recyclerViewAllServices.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allServicesAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun loadTopEvents() {
        topEventsList.addAll(getDummyEvents().take(5))
        topEventsAdapter.notifyDataSetChanged()
    }

    private fun loadAllEvents() {
        val events = getDummyEvents()
        if (!allEventsLoaded) {
            allEventsList.clear()
            allEventsList.addAll(events.take(5))
            allEventsAdapter.notifyDataSetChanged()

            if (events.size > 5) {
                binding.buttonLoadMoreEvents.visibility = View.VISIBLE
            } else {
                binding.buttonLoadMoreEvents.visibility = View.GONE
            }
        } else {
            allEventsList.clear()
            allEventsList.addAll(events)
            allEventsAdapter.notifyDataSetChanged()
            binding.buttonLoadMoreEvents.visibility = View.GONE
        }
    }

    private fun loadTopServices() {
        topServicesList.addAll(getDummyServices().take(5))
        topServicesAdapter.notifyDataSetChanged()
    }

    private fun loadAllServices() {
        val services = getDummyServices()
        if (!allServicesLoaded) {
            allServicesList.clear()
            allServicesList.addAll(services.take(5))
            allServicesAdapter.notifyDataSetChanged()

            if (services.size > 5) {
                binding.buttonLoadMoreServicesProducts.visibility = View.VISIBLE
            } else {
                binding.buttonLoadMoreServicesProducts.visibility = View.GONE
            }
        } else {
            allServicesList.clear()
            allServicesList.addAll(services)
            allServicesAdapter.notifyDataSetChanged()
            binding.buttonLoadMoreServicesProducts.visibility = View.GONE
        }
    }

    private fun setupSearchFunctionality() {
        binding.editTextSearchEvents.addTextChangedListener {
            val query = it.toString()
            searchEvents(query)
        }

        binding.editTextSearchServices.addTextChangedListener {
            val query = it.toString()
            searchServices(query)
        }
    }

    private fun searchEvents(query: String) {
        val events = if (allEventsLoaded) getDummyEvents() else getDummyEvents().take(5)
        val filteredList = events.filter { it.name.contains(query, true) }
        allEventsAdapter.updateList(filteredList)
    }

    private fun searchServices(query: String) {
        val services = if (allServicesLoaded) getDummyServices() else getDummyServices().take(5)
        val filteredList = services.filter { it.name.contains(query, true) }
        allServicesAdapter.updateList(filteredList)
    }

    private fun handleItemClicks() {
        topEventsAdapter.setOnItemClickListener { event ->
            navigateToEventDetails(event)
        }
        allEventsAdapter.setOnItemClickListener { event ->
            navigateToEventDetails(event)
        }

        topServicesAdapter.setOnItemClickListener { service ->
            navigateToServiceDetails(service)
        }
        allServicesAdapter.setOnItemClickListener { service ->
            navigateToServiceDetails(service)
        }
    }

    private fun navigateToEventDetails(event: Event) {
        val fragment = EventInfoFragment.newInstance(event)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToServiceDetails(service: Service) {
        val fragment = ServiceProductInfoFragment.newInstance(service)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getDummyEvents(): List<Event> {
        return (1..20).map {
            Event("Event $it", "Description $it", "City", "Location", "Open")
        }
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
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "2",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "3",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "4",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "5",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "6",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "7",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
            Service(
                id = "8",
                name = "Catering Service",
                description = "Delicious food",
                characteristics = "Includes vegetarian options",
                price = 1000.0,
                discount = 10.0,
                images = listOf(),
                eventTypes = listOf("1", "2"),
                categoryId = "2",
                visibility = true,
                availability = true,
                bookingDuration = 60,
                minParticipation = null,
                maxParticipation = null,
                bookingPeriod = 30,
                cancellationPeriod = 7,
                confirmationMethod = ConfirmationMethod.AUTOMATIC
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
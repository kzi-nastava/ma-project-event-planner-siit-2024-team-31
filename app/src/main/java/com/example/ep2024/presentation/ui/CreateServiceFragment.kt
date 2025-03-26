package com.example.ep2024.presentation.ui

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ep2024.databinding.FragmentCreateServiceBinding
import com.example.ep2024.domain.model.Service
import com.example.ep2024.domain.model.service.ConfirmationMethod
import java.util.UUID

class CreateServiceFragment : Fragment() {

    private var _binding: FragmentCreateServiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var categories: List<String>
    private lateinit var eventTypes: List<String>
    private val selectedEventTypes = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize categories and event types
        categories = listOf("Culinary", "Entertainment", "Transport")
        eventTypes = listOf("Wedding", "Corporate", "Birthday", "Concert", "Sports Event")

        setupCategorySpinner()
        setupEventTypesSelector()
        setupButtons()
    }

    private fun setupCategorySpinner() {
        val categoryAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = categoryAdapter

        binding.buttonAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun showAddCategoryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add New Category")

        val input = android.widget.EditText(requireContext())
        input.hint = "Category Name"
        builder.setView(input)

        builder.setPositiveButton("Submit") { dialog, _ ->
            val categoryName = input.text.toString().trim()
            if (categoryName.isNotEmpty()) {
                // Implement logic to send the new category proposal to admin
                Toast.makeText(
                    requireContext(),
                    "Category proposal sent to admin",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Category name cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun setupEventTypesSelector() {
        binding.textViewSelectedEventTypes.setOnClickListener {
            showEventTypesDialog()
        }
    }

    private fun showEventTypesDialog() {
        val selectedItems = selectedEventTypes.toMutableList()
        val checkedItems = BooleanArray(eventTypes.size) { eventTypes[it] in selectedItems }

        AlertDialog.Builder(requireContext())
            .setTitle("Select Event Types")
            .setMultiChoiceItems(eventTypes.toTypedArray(), checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(eventTypes[which])
                } else {
                    selectedItems.remove(eventTypes[which])
                }
            }
            .setPositiveButton("OK") { dialog, _ ->
                selectedEventTypes.clear()
                selectedEventTypes.addAll(selectedItems)
                binding.textViewSelectedEventTypes.text = selectedEventTypes.joinToString(", ")
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupButtons() {
        binding.buttonUploadImages.setOnClickListener {
            // Implement image upload functionality
            Toast.makeText(requireContext(), "Image upload not implemented", Toast.LENGTH_SHORT).show()
        }

        binding.buttonSubmitService.setOnClickListener {
            // Implement form submission logic
            if (validateForm()) {


                Toast.makeText(
                    requireContext(),
                    "Service submitted for moderation",
                    Toast.LENGTH_SHORT
                ).show()

                // Navigate back to MyServicesFragment
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun validateForm(): Boolean {
        return when {
            TextUtils.isEmpty(binding.editTextServiceName.text.toString().trim()) -> {
                binding.editTextServiceName.error = "Service name is required"
                false
            }
            TextUtils.isEmpty(binding.editTextServiceDescription.text.toString().trim()) -> {
                binding.editTextServiceDescription.error = "Service description is required"
                false
            }
            TextUtils.isEmpty(binding.editTextServicePrice.text.toString().trim()) -> {
                binding.editTextServicePrice.error = "Price is required"
                false
            }
            TextUtils.isEmpty(binding.editTextBookingPeriod.text.toString().trim()) -> {
                binding.editTextBookingPeriod.error = "Booking period is required"
                false
            }
            TextUtils.isEmpty(binding.editTextCancellationPeriod.text.toString().trim()) -> {
                binding.editTextCancellationPeriod.error = "Cancellation period is required"
                false
            }
            selectedEventTypes.isEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Please select at least one event type.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            else -> true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
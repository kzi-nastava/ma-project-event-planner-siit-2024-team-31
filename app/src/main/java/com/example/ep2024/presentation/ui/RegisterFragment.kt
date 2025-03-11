package com.example.ep2024.presentation.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ep2024.MainActivity
import com.example.ep2024.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRoles: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userRoles = listOf("OD", "PUP", "AK")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, userRoles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerUserRole.adapter = adapter

        binding.spinnerUserRole.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedRole = userRoles[position]
                updateFormForRole(selectedRole)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val defaultRole = binding.spinnerUserRole.selectedItem.toString()
        updateFormForRole(defaultRole)

        binding.buttonRegister.setOnClickListener {
            val selectedRole = binding.spinnerUserRole.selectedItem.toString()

            when (selectedRole) {
                "OD", "AK" -> {
                    val email = binding.editTextEmail.text.toString()
                    val password = binding.editTextPassword.text.toString()
                    val confirmPassword = binding.editTextConfirmPassword.text.toString()
                    val firstName = binding.editTextFirstName.text.toString()
                    val lastName = binding.editTextLastName.text.toString()
                    val address = binding.editTextAddress.text.toString()
                    val phone = binding.editTextPhone.text.toString()

                    //TODO: photo load

                    //TODO: reg for OD and AK
                }
                "PUP" -> {
                    val email = binding.editTextEmail.text.toString()
                    val password = binding.editTextPassword.text.toString()
                    val confirmPassword = binding.editTextConfirmPassword.text.toString()
                    val companyName = binding.editTextCompanyName.text.toString()
                    val companyAddress = binding.editTextCompanyAddress.text.toString()
                    val companyPhone = binding.editTextCompanyPhone.text.toString()
                    val companyDescription = binding.editTextCompanyDescription.text.toString()

                    //TODO: photo load

                    //TODO: reg for PUP
                }
            }

            (activity as MainActivity).isLoggedIn = true
            (activity as MainActivity).userRole = selectedRole
            (activity as MainActivity).updateBottomNavigationMenu()

            findNavController().navigate(com.example.ep2024.R.id.mainPageFragment)
        }
    }

    private fun updateFormForRole(role: String) {
        when (role) {
            "OD", "AK" -> {
                binding.layoutODandAK.visibility = View.VISIBLE
                binding.layoutPUP.visibility = View.GONE
            }
            "PUP" -> {
                binding.layoutPUP.visibility = View.VISIBLE
                binding.layoutODandAK.visibility = View.GONE
            }
            else -> {
                binding.layoutODandAK.visibility = View.GONE
                binding.layoutPUP.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
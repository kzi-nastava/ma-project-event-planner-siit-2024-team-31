package com.example.ep2024.ui.fragments.auth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.entity.user.Role
import com.example.ep2024.databinding.FragmentRegisterBinding
import com.example.ep2024.ui.utils.ImagePreviewAdapter
import com.example.ep2024.ui.viewModels.auth.types.RegisterEvent
import com.example.ep2024.ui.viewModels.auth.RegisterViewModel
import com.example.ep2024.ui.viewModels.auth.types.RegisterResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var portfolioAdapter: ImagePreviewAdapter

    private val pickProfileImageLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        viewModel.onEvent(RegisterEvent.ProfileImagePicked(uri))
    }

    private val pickPortfolioImagesLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            viewModel.onEvent(RegisterEvent.PortfolioImagesPicked(uris))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRoleSpinner()
        setupListeners()
        observeState()
    }

    private fun setupRecyclerView() {
        portfolioAdapter = ImagePreviewAdapter()
        binding.rvPortfolioImages.adapter = portfolioAdapter
    }

    private fun setupListeners() {
        binding.etEmail.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.EmailChanged(text.toString())) }
        binding.etPassword.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.PasswordChanged(text.toString())) }
        binding.etConfirmPassword.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(text.toString())) }
        binding.etFirstName.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.FirstNameChanged(text.toString())) }
        binding.etLastName.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.LastNameChanged(text.toString())) }
        binding.etPhoneNumber.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.PhoneNumberChanged(text.toString())) }
        binding.etCompanyName.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.CompanyNameChanged(text.toString())) }
        binding.etCompanyPhoneNumber.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.CompanyPhoneNumberChanged(text.toString())) }
        binding.etDescription.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.DescriptionChanged(text.toString())) }
        binding.etCountry.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.CountryChanged(text.toString())) }
        binding.etCity.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.CityChanged(text.toString())) }
        binding.etStreetAddress.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.AddressChanged(text.toString())) }
        binding.etZipCode.doOnTextChanged { text, _, _, _ -> viewModel.onEvent(RegisterEvent.ZipCodeChanged(text.toString())) }

        binding.btnSelectImage.setOnClickListener {
            pickProfileImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnAddPortfolioImages.setOnClickListener {
            pickPortfolioImagesLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(RegisterEvent.Register)
        }
    }

    private fun setupRoleSpinner() {
        val roles = listOf(Role.USER, Role.OD, Role.PUP)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            roles.map { it.displayedString }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerRole.adapter = adapter

        binding.spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRole = roles[position]
                viewModel.onEvent(RegisterEvent.RoleChanged(selectedRole))
                updateUiForRole(selectedRole)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                updateUiForRole(null)
            }
        }
        updateUiForRole(roles[0])
    }

    private fun updateUiForRole(role: Role?) {
        binding.apply {
            cardPersonal.isVisible = false
            cardCompany.isVisible = false
            cardSinglePhoto.isVisible = false
            cardMultiPhoto.isVisible = false
            cardAddress.isVisible = role != null

            when (role) {
                Role.USER -> {
                    cardPersonal.isVisible = true
                    cardSinglePhoto.isVisible = true
                    (btnRegister.layoutParams as ConstraintLayout.LayoutParams).topToBottom = cardSinglePhoto.id
                }
                Role.OD -> {
                    cardPersonal.isVisible = true
                    cardMultiPhoto.isVisible = true
                    (btnRegister.layoutParams as ConstraintLayout.LayoutParams).topToBottom = cardMultiPhoto.id
                }
                Role.PUP -> {
                    cardCompany.isVisible = true
                    cardMultiPhoto.isVisible = true
                    (btnRegister.layoutParams as ConstraintLayout.LayoutParams).topToBottom = cardMultiPhoto.id
                }
                Role.ADMIN, null -> {
                    cardAddress.isVisible = false
                }
            }
            val topAnchorId = when {
                cardPersonal.isVisible -> cardPersonal.id
                cardCompany.isVisible -> cardCompany.id
                else -> cardAccount.id
            }
            (cardAddress.layoutParams as ConstraintLayout.LayoutParams).topToBottom = topAnchorId
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        binding.progressBar.isVisible = state.isLoading
                        binding.btnRegister.isEnabled = !state.isLoading

                        portfolioAdapter.submitList(state.photos)

                        binding.ivProfilePicture.setImageURI(state.photos.firstOrNull())
                    }
                }
                launch {
                    viewModel.registerResult.collect { result ->
                        when (result) {
                            is RegisterResult.Success -> {
                                Snackbar.make(binding.root, "Registration successful. Please check your email.", Snackbar.LENGTH_LONG).show()
                                findNavController().navigateUp()
                            }
                            is RegisterResult.Error -> {
                                Snackbar.make(binding.root, result.message, Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
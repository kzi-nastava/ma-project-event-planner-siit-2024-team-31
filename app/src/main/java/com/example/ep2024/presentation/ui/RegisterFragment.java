package com.example.ep2024.presentation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ep2024.MainActivity;
import com.example.ep2024.R;
import com.example.ep2024.databinding.FragmentRegisterBinding;
import com.example.ep2024.domain.model.Role;
import com.example.ep2024.domain.repository.AuthRepository;
import com.example.ep2024.domain.usecase.auth.RegisterUseCase;

import java.util.List;

import javax.inject.Inject;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Inject
    AuthRepository authRepository;

    private RegisterUseCase registerUseCase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        // Инъекция зависимостей
        assert getActivity() != null;
        ((MainActivity) getActivity()).getAppComponent().inject(this);

        registerUseCase = new RegisterUseCase(authRepository);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Role> roles = Role.getRolesListWithoutAdminRole();
        ArrayAdapter<Role> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerUserRole.setAdapter(adapter);

        binding.spinnerUserRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Role selectedRole = (Role) parent.getItemAtPosition(position);
                updateFormForRoleSelected(selectedRole);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Role selectedRole = (Role) binding.spinnerUserRole.getSelectedItem();

                String email, password, confirmPassword, firstName, lastName, country,
                        city, street, zipCode, phone, companyName, description;

                switch (selectedRole)
                {
                    case USER:
                        email = binding.editTextEmail.getText().toString();
                        password = binding.editTextPassword.getText().toString();
                        confirmPassword = binding.editTextConfirmPassword.getText().toString();
                        firstName = binding.editTextFirstName.getText().toString();
                        lastName = binding.editTextLastName.getText().toString();
                        country = binding.editTextCountry.getText().toString();
                        city = binding.editTextCity.getText().toString();
                        street = binding.editTextStreet.getText().toString();
                        zipCode = binding.editTextZipCode.getText().toString();
                        phone = binding.editTextPhoneNumber.getText().toString();
                        // TODO: photos

                        break;

                    case OD:
                        email = binding.editTextEmail.getText().toString();
                        password = binding.editTextPassword.getText().toString();
                        confirmPassword = binding.editTextConfirmPassword.getText().toString();
                        firstName = binding.editTextFirstNameOD.getText().toString();
                        lastName = binding.editTextLastNameOD.getText().toString();
                        country = binding.editTextCountryOD.getText().toString();
                        city = binding.editTextCityOD.getText().toString();
                        street = binding.editTextStreetOD.getText().toString();
                        zipCode = binding.editTextZipCodeOD.getText().toString();
                        phone = binding.editTextPhoneOD.getText().toString();
                        // TODO: photos

                        break;
                    case PUP:
                        email = binding.editTextEmail.getText().toString();
                        password = binding.editTextPassword.getText().toString();
                        confirmPassword = binding.editTextConfirmPassword.getText().toString();
                        companyName = binding.editTextCompanyName.getText().toString();
                        country = binding.editTextCountryPUP.getText().toString();
                        city = binding.editTextCityPUP.getText().toString();
                        street = binding.editTextStreetPUP.getText().toString();
                        zipCode = binding.editTextZipCodePUP.getText().toString();
                        phone = binding.editTextPhonePUP.getText().toString();
                        description = binding.editTextCompanyDescription.getText().toString();
                        //TODO: photos

                        break;

                }

                if (getActivity() != null) {
                    ((MainActivity) getActivity()).updateBottomNavigationMenu();
                }

                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.mainPageFragment);
            }
        });
    }

    private void updateFormForRoleSelected(Role role) {
        binding.layoutUser.setVisibility(View.GONE);
        binding.layoutOD.setVisibility(View.GONE);
        binding.layoutPUP.setVisibility(View.GONE);

        switch (role) {
            case OD:
                binding.layoutOD.setVisibility(View.VISIBLE);
                break;
            case PUP:
                binding.layoutPUP.setVisibility(View.VISIBLE);
                break;
            case USER:
                binding.layoutUser.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.ep2024.presentation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.ep2024.MainActivity;
import com.example.ep2024.R;
import com.example.ep2024.config.SecureStorage;
import com.example.ep2024.databinding.FragmentLoginBinding;
import com.example.ep2024.domain.model.Role;
import com.example.ep2024.domain.repository.AuthRepository;
import com.example.ep2024.domain.usecase.auth.LoginUseCase;
import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO;
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO;
import com.google.gson.Gson;

import javax.inject.Inject;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Role userRole;

    @Inject
    SecureStorage secureStorage;

    @Inject
    AuthRepository authRepository;

    private LoginUseCase loginUseCase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        assert getActivity() != null;
        ((MainActivity) getActivity()).getAppComponent().inject(this);

        loginUseCase = new LoginUseCase(authRepository);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.editTextEmail.getText().toString().trim();
                System.out.printf("email: %s\n", email);

                String password = binding.editTextPassword.getText().toString().trim();
                System.out.printf("password: %s\n", password);

                //make it in a separate thread to avoid blocking the UI
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);
                            LoginResponseDTO loginResponse = loginUseCase.execute(loginRequest);

                            secureStorage.saveUser(new Gson().toJson(loginResponse));

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity mainActivity = (MainActivity) getActivity();
                                        if (mainActivity != null) {
                                            mainActivity.updateBottomNavigationMenu();
                                        }
                                        NavHostFragment.findNavController(LoginFragment.this)
                                                .navigate(R.id.mainPageFragment);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Auth Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
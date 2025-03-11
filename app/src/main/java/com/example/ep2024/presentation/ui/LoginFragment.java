package com.example.ep2024.presentation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.ep2024.MainActivity;
import com.example.ep2024.R;
import com.example.ep2024.databinding.FragmentLoginBinding;
import com.example.ep2024.domain.model.Role;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Role userRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: login check

                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.setIsLoggedIn(true);
                    mainActivity.setUserRole(userRole);
                    mainActivity.updateBottomNavigationMenu();
                }
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.mainPageFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
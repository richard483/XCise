package com.example.xcise;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    TextView welcomeHome, caloriesInfo, workoutInfo, typeInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = requireActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        welcomeHome = view.findViewById(R.id.welcome_home_tv);
        caloriesInfo = view.findViewById(R.id.calories_info_tv);
        workoutInfo = view.findViewById(R.id.workout_info_tv);
        typeInfo = view.findViewById(R.id.type_info_tv);

        welcomeHome.setText("Welcome, " + sp.getString("name", "null") + "!");
        caloriesInfo.setText("12345");
        workoutInfo.setText("12345");
        typeInfo.setText("12345");
    }
}
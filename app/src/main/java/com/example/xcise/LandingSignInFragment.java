package com.example.xcise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LandingSignInFragment extends Fragment {
    TextView signupTextView;
    EditText email, password;
    Button signIn;
    SharedPreferences sp;
    FirebaseAuth auth;

    View.OnClickListener toSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.frame_landing, new SignUpFragment())
                    .addToBackStack(null).commit();
        }
    };

    View.OnClickListener doSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailStr = email.getText().toString();
            String passwordStr = password.getText().toString();

            auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(requireActivity(), "Success Sign Up, directing to home", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name", task.getResult().getUser().getDisplayName());
                        editor.putString("email", task.getResult().getUser().getEmail());
                        editor.apply();

                        Intent toMain = new Intent(requireActivity(), MainActivity.class);
                        startActivity(toMain);
                    }else{
                        Toast.makeText(requireActivity(), "ERROR Sign In Exception:/n" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupTextView = view.findViewById(R.id.signup_tv);
        email = view.findViewById(R.id.email_et);
        password = view.findViewById(R.id.password_et);
        signIn = view.findViewById(R.id.signin_btn);
        auth = FirebaseAuth.getInstance();
        sp = requireActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);


        signupTextView.setOnClickListener(toSignUp);
        signIn.setOnClickListener(doSignIn);
    }
}
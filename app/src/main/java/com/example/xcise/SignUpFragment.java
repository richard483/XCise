package com.example.xcise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpFragment extends Fragment {

    EditText fullName, email, password, rePassword;
    String fullNameStr, emailStr, passwordStr, rePasswordStr;
    String alert = "";
    Button signUpBtn;

    private FirebaseAuth auth;

    View.OnClickListener signUp = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            fullNameStr = fullName.getText().toString();
            emailStr = email.getText().toString();
            passwordStr = password.getText().toString();
            rePasswordStr = rePassword.getText().toString();
            if(validator(fullNameStr, passwordStr, rePasswordStr, emailStr)){
                auth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    UserProfileChangeRequest insertName = new UserProfileChangeRequest.Builder().setDisplayName(fullNameStr).build();

                                    task.getResult().getUser().updateProfile(insertName);
                                    Toast.makeText(requireActivity(), "Register Success, directing to Home", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(requireActivity(), "Register unseccessful ERROR:\n" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(requireActivity(), alert, Toast.LENGTH_SHORT).show();
                alert = "";
            }
        }
    };


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullName = view.findViewById(R.id.full_name_et);
        email = view.findViewById(R.id.email_et);
        password = view.findViewById(R.id.password_et);
        rePassword = view.findViewById(R.id.re_password_et);
        signUpBtn = view.findViewById(R.id.signup_btn);

        auth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(signUp);

    }

    public boolean validator(String name, String password, String repassword, String email){
        if(!name.isEmpty()){
            if(name.length()>=5){
                if(!email.isEmpty()){
                    if(email.contains("@")){
                        if(email.endsWith(".com")){
                            if((!password.isEmpty())&&(!repassword.isEmpty())){
                                if(password.equals(repassword)){
                                    return true;
                                }else {
                                    alert += "Password and Reenter password must be same\n";
                                }
                            }else {
                                alert+="Password and Re-enter password cannot be empty!\n";
                            }
                        }else{
                            alert += "Email must ends with '.com'\n";
                        }
                    }else{
                        alert += "Email must contain '@' character\n";
                    }
                }else{
                    alert+="Email cannot be empty!\n";
                }
            }else {
                alert += "Name must be at least 5 characters\n";
            }
        }else {
            alert+="Name cannot be empty!\n";
        }

        return  false;
    }
}
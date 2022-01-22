package com.securerecordbook.cashbook;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.securerecordbook.cashbook.databinding.FragmentAuthBinding;

public class AuthFragment extends Fragment {

    // View Binding
    FragmentAuthBinding fragmentAuthBinding;

    // Phone Auth
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mPhoneVerificationCallbacks;

    private String mVerificationId;

    private FirebaseAuth firebaseAuth;

    private TextInputEditText countryCode;

    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        fragmentAuthBinding = FragmentAuthBinding.inflate(R.layout.fragment_auth, container, false);
//
//        View view = inflater.inflate(R.layout.fragment_auth, container, false);
//
//        countryCode = view.findViewById(R.id.countryCode);
//        countryCode.setInputType(InputType.TYPE_NULL);
//        countryCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideKeyboard(getActivity(), getActivity().getCurrentFocus());
//            }
//        });
//        return view;
        fragmentAuthBinding = FragmentAuthBinding.inflate(getLayoutInflater());
        return fragmentAuthBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentAuthBinding.countryCodeValue.setInputType(InputType.TYPE_NULL);
        fragmentAuthBinding.countryCodeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity(), getActivity().getCurrentFocus());
            }
        });
    }

    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
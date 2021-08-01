package com.example.duan1.ui.userProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duan1.LoginActivity;
import com.example.duan1.R;
import com.example.duan1.databinding.FragmentHomeBinding;
import com.example.duan1.databinding.FragmentUserProfileBinding;
import com.example.duan1.ui.home.HomeViewModel;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfileFragment extends Fragment {

//    private UserProfileViewModel userProfileViewModel;
    private FragmentUserProfileBinding binding;
    Button btnUpdate, btnLogout;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    ImageView profileImg;
    Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    StorageReference storageReference;
    TextView fullName, userNameField;
    String userId;
    TextInputLayout user, email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        userProfileViewModel =
//                new ViewModelProvider(this).get(UserProfileViewModel.class);

       // mContext = container.getContext();
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.tvUserName;
//        userProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                textView.setText(s);
//            }
//        });


        fullName = (TextView) root.findViewById(R.id.tv_userName);
        userNameField = (TextView) root.findViewById(R.id.tv_userNameField);
        email = (TextInputLayout) root.findViewById(R.id.email);
        user = (TextInputLayout) root.findViewById(R.id.userName);
        profileImg = (ImageView) root.findViewById(R.id.userAvatar);

        btnLogout = (Button) root.findViewById(R.id.btn_logout);
        btnUpdate = (Button) root.findViewById(R.id.btn_updateProfile);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        fUser = fAuth.getCurrentUser();

            DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        fullName.setText(documentSnapshot.getString("fname"));
                        userNameField.setText(documentSnapshot.getString("fname"));
                        user.getEditText().setText(documentSnapshot.getString("fname"));
                        email.getEditText().setText(documentSnapshot.getString("email"));

                }
            });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().onBackPressed();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
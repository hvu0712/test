package com.example.duan1.ui.userProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.LoginActivity;
import com.example.duan1.R;
import com.example.duan1.databinding.FragmentHomeBinding;
import com.example.duan1.databinding.FragmentUserProfileBinding;
import com.example.duan1.ui.home.HomeViewModel;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    Button btnUpdate, btnLogout, btn_reName;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    ImageView profileImg;
    Context mContext;
    TextView fullName, userNameField;
    String userId;
    TextInputLayout user, email;
    StorageReference storageReference;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        fullName = (TextView) root.findViewById(R.id.tv_userName);
        userNameField = (TextView) root.findViewById(R.id.tv_userNameField);
        email = (TextInputLayout) root.findViewById(R.id.email);
        user = (TextInputLayout) root.findViewById(R.id.userName);
        profileImg = (ImageView) root.findViewById(R.id.userAvatar);



        btnLogout = (Button) root.findViewById(R.id.btn_logout);
        btnUpdate = (Button) root.findViewById(R.id.btn_updateProfile);
        btn_reName = (Button) root.findViewById(R.id.btn_reName);



        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference imgRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImg);
            }
        });

        userId = fAuth.getCurrentUser().getUid();
        fUser = fAuth.getCurrentUser();

            DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e == null){
                            fullName.setText(documentSnapshot.getString("fname"));
                            userNameField.setText(documentSnapshot.getString("fname"));
                            user.getEditText().setText(documentSnapshot.getString("fname"));
                        }

                }
            });

        btn_reName.setOnClickListener(new View.OnClickListener() {
            DocumentReference documentReference = fStore.collection("users").document(userId);
            @Override
            public void onClick(View v) {
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null){
                            String reName = user.getEditText().getText().toString();
                            useReName();
                            fullName.setText(value.getString(reName));
                            userNameField.setText(value.getString(reName));
                            startActivity(getActivity().getIntent());
                            Toast.makeText(getActivity(), "?????i t??n th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imgUri = data.getData();
//                imgUser.setImageURI(imgUri);
                uploadImgToFirebase(imgUri);
            }
        }
    }

    public void useReName(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();
        fUser = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        Map<String, Object> user_reName = new HashMap<>();
        String reName = user.getEditText().getText().toString();
        user_reName.put("fname", reName);
        documentReference.set(user_reName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void uploadImgToFirebase(Uri imgUri) {
        // upload img to firebase storage
        StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImg);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "T???i l??n th???t b???i", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
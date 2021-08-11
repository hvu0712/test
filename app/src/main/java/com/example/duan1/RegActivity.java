package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.perfmark.Tag;


public class RegActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputLayout fname, pass, phone, email;
    TextView fNameFiled, fUser;
    Button btn_reg, btn_reg_login;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        fname = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        btn_reg = findViewById(R.id.btn_reg);
        btn_reg_login = findViewById(R.id.btn_reg_login);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        getSupportActionBar().hide();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();

                if (!validateUser() || !validatePass() || !validateEmail()){
                    return;
                } else {
                    String fullname = fname.getEditText().getText().toString();
                    String passWord = pass.getEditText().getText().toString();
                    String Email =  email.getEditText().getText().toString();

                    mAuth.createUserWithEmailAndPassword(Email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser fUser = mAuth.getCurrentUser();
                                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(), "Email xác nhận đã được gửi", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());                                    }
                                });
                                    Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                    userId = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(userId);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fname", fullname);
                                    user.put("email", Email);
                                    user.put("password", passWord);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: user Profile is created for "+ userId);                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });
                            } else {
                                Toast.makeText(getApplicationContext(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    public Boolean validateUser(){
        String val = fname.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()){
            fname.setError("Không được để trống");
            return false;
        } else if(val.length() >= 15){
            fname.setError("Tên đăng nhập không quá 15 kí tự");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            fname.setError("Ít nhất 4 kí tự và không có khoảng trắng");
            return false;
        }
        else {
            fname.setError(null);
            fname.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validatePhone(){
        String val = phone.getEditText().getText().toString();

        if (val.isEmpty()){
            phone.setError("Không được để trống");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validateEmail(){
        String val = email.getEditText().getText().toString();
        String emailVal = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";

        if (val.isEmpty()){
            email.setError("Không được để trống");
            return false;
        } else if(!val.matches(emailVal)){
            email.setError("Email không đúng định dạng");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validatePass(){
        String val = pass.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()){
            pass.setError("Không được để trống");
            return false;
        } else if(!val.matches(passwordVal)){
            pass.setError("Mật khẩu quá yếu");
            return false;
        } else if(val.length() < 4){
            pass.setError("Mật khẩu có ít nhất 4 kí tự");
            return false;
        } else {
            pass.setError(null);
            pass.setErrorEnabled(false);
            return true;
        }
    }

    public void goLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
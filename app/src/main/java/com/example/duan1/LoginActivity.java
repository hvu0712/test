package com.example.duan1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {

    TextInputLayout email, pass;
    Button btn_login, btn_login_reg;
    Button tv_forgotPassword;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btn_login = findViewById(R.id.btn_login);
        tv_forgotPassword = findViewById(R.id.tv_forgotPassword);
        btn_login_reg = findViewById(R.id.btn_login_reg);

        getSupportActionBar().hide();

        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder resetPassDialog = new AlertDialog.Builder(v.getContext());
                resetPassDialog.setTitle("Đổi mật khẩu ");
                resetPassDialog.setMessage("Nhập email của bạn để đổi mật khẩu");
                resetPassDialog.setView(resetMail);

                resetPassDialog.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (resetMail.length()<=0){
                            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            String mail = resetMail.getText().toString();
                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(LoginActivity.this, "Link đổi mật khẩu đã được gửi đến email của bạn", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Error ! " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

                resetPassDialog.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                resetPassDialog.create().show();
            }
        });
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

        if (val.isEmpty()){
            pass.setError("Không được để trống");
            return false;
        } else {
            pass.setError(null);
            pass.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        if (!validateEmail() || !validatePass()){
            return;
        } else {
            isUser();
            finish();
        }
    }

    private void isUser() {
        String passWord = pass.getEditText().getText().toString();
        String Email =  email.getEditText().getText().toString();

        fAuth.signInWithEmailAndPassword(Email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        finish();
    }

    public void goReg(View view){
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
        finish();
    }


}
package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView tv_ngLieu, tv_cachLam, tv_cheBien;
    ImageView img_detail;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_ngLieu = findViewById(R.id.tv_nglieu);
        tv_cachLam = findViewById(R.id.tv_cachLam);
        tv_cheBien = findViewById(R.id.tv_cheBien);
        img_detail = findViewById(R.id.img_detail);

        getSupportActionBar().hide();
        String foodKey = getIntent().getStringExtra("FoodKey");
        ref = FirebaseDatabase.getInstance().getReference().child("foods");
        ref.child(foodKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String ngLieu = snapshot.child("ngLieu").getValue().toString().replace("\\n", "\n");
                    String cachLam = snapshot.child("cachLam").getValue().toString().replace("\\n", "\n");
                    String imgFood = snapshot.child("imgDetail").getValue().toString();
                    String cheBien = snapshot.child("cheBien").getValue().toString().replace("\\n", "\n");
                    tv_ngLieu.setText(ngLieu);
                    tv_cachLam.setText(cachLam);
                    tv_cheBien.setText(cheBien);
                    Picasso.get().load(imgFood).into(img_detail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.duan1.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DetailActivity;
import com.example.duan1.R;
import com.example.duan1.databinding.FragmentHomeBinding;
import com.example.duan1.models.Ricipe;
import com.example.duan1.viewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    RecyclerView rv_food;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Ricipe> options;
    EditText edt_searchBar;
    FirebaseRecyclerAdapter<Ricipe, FoodViewHolder> adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edt_searchBar = root.findViewById(R.id.edt_searchBar);
        rv_food = (RecyclerView) root.findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
        loadData("");
        edt_searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=null){
                    loadData(s.toString());
                } else {
                    loadData("");
                }
            }
        });

        return root;
    }

    public void loadData(String data){

        Query query = databaseReference.orderByChild("title").startAt(data).endAt(data+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Ricipe>()
                .setQuery(query, Ricipe.class).build();

        adapter = new FirebaseRecyclerAdapter<Ricipe, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Ricipe model) {

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("FoodKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
                Picasso.get().load(model.getImgFoodLink()).into(holder.foodImg, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                holder.foodTitle.setText(model.getTitle());

            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_recipe_item,parent,false);
                return new FoodViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        RecyclerView.ItemDecoration itemDecoration1 = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rv_food.addItemDecoration(itemDecoration);
        rv_food.addItemDecoration(itemDecoration1);
        rv_food.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        rv_food.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        if (adapter!=null){
            adapter.notifyDataSetChanged();
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
            adapter.startListening();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
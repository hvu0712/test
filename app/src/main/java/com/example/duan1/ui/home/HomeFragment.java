package com.example.duan1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.duan1.R;
import com.example.duan1.adapters.CustomAdapter;
import com.example.duan1.databinding.FragmentHomeBinding;
import com.example.duan1.models.Ricipe;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        List<Ricipe> img_details = getListData();
        final ListView listView = (ListView) root.findViewById(R.id.lv_home);
        listView.setAdapter(new CustomAdapter(img_details, getActivity()));

        return root;
    }

    private List<Ricipe> getListData(){
        List<Ricipe> list = new ArrayList<Ricipe>();
        Ricipe ricardo = new Ricipe("Ricardo", "Ricardo");

        list.add(ricardo);
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
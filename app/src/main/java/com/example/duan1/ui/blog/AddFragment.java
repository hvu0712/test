package com.example.duan1.ui.blog;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.duan1.R;
import com.example.duan1.databinding.FragmentAddBinding;

public class AddFragment extends Fragment  {

    private AddViewModel addViewModel;
    private FragmentAddBinding binding;
    WebView wv_blog;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // đổ web vào
        wv_blog = root.findViewById(R.id.wv_blog);
        WebSettings webSettings = wv_blog.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv_blog.setWebViewClient(new WebViewClient());
        wv_blog.loadUrl("https://hvu0712.wordpress.com/");

        // nút back để quay lại trang vừa xem trong wv
        wv_blog.canGoBack();
        wv_blog.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && wv_blog.canGoBack()) {
                    wv_blog.goBack();
                    return true;
                }
                return false;
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
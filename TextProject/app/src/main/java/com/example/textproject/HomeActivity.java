package com.example.textproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.textproject.databinding.ActivityHomeBinding;
import com.example.textproject.ui.Bookmark.BookmarkFragment;
import com.example.textproject.ui.home.HomeFragment;
import com.example.textproject.ui.my_courses.MyCoursesFragment;
import com.example.textproject.ui.profile.ProfileFragment;
import com.example.textproject.ui.sign_in.SignInActivity;
import com.example.textproject.utils.SharedPrefsUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding binding;
    private HomeActivityViewModel viewModel;
    private SharedPrefsUtils prefsUtils;

    private final int defaultFragment = 0;
    private int currentFragment = defaultFragment;
    private final ArrayList<Fragment> fragmentArrays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        prefsUtils = new SharedPrefsUtils(getBaseContext());


        binding.tvUserName.setOnClickListener(v -> {
            setFragment(3);
        });


        viewModel.currentUser.observe(this, user -> {
            if (user != null) {
                binding.ivUserImageHome.setImageBitmap(convertByteArrayToBitmap(user.getUserImage()));
                binding.tvUserName.setText(user.getName());
            } else {
                binding.ivUserImageHome.setImageResource(R.drawable.ic_launcher_foreground);
                binding.tvUserName.setText("Guest");
            }
        });


        fragmentArrays.add(new HomeFragment());
        fragmentArrays.add(new BookmarkFragment());
        fragmentArrays.add(new MyCoursesFragment());
        fragmentArrays.add(new ProfileFragment());

        initFragments();

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int fragmentOrder = item.getOrder();
            if (currentFragment != fragmentOrder) {
                setFragment(fragmentOrder);
            }
            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            } else if (currentFragment != defaultFragment) {
                setFragment(defaultFragment);
            } else {
                finish();
            }
        }
    };

    private void initFragments() {
        FragmentTransaction transactionAdd = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragmentArrays.size(); i++) {
            transactionAdd.add(R.id.fragmentsContainer, fragmentArrays.get(i));
        }
        transactionAdd.commit();

        FragmentTransaction transactionHide = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragmentArrays.size(); i++) {
            if (i != defaultFragment) {
                transactionHide.hide(fragmentArrays.get(i));
            }
        }
        transactionHide.commit();
    }

    private void setFragment(int fragmentIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(fragmentArrays.get(fragmentIndex));
        transaction.hide(fragmentArrays.get(currentFragment));
        transaction.commit();

        currentFragment = fragmentIndex;
    }

    private Bitmap convertByteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutItem) {
            prefsUtils.removeUserId();
            startActivity(new Intent(getBaseContext(), SignInActivity.class));
            finish();
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}

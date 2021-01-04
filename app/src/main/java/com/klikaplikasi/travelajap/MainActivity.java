package com.klikaplikasi.travelajap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.klikaplikasi.travelajap.fragment.AkunFragment;
import com.klikaplikasi.travelajap.fragment.BantuanFragment;
import com.klikaplikasi.travelajap.fragment.BerandaFragment;
import com.klikaplikasi.travelajap.fragment.PesananFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new BerandaFragment());

        bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.menu_beranda :
                        fragment = new BerandaFragment();
                        break;
                    case R.id.menu_pesanan :
                        fragment = new PesananFragment();
                        break;

                    case R.id.menu_bantuan :
                        fragment = new BantuanFragment();
                        break;

                    case R.id.menu_akun :
                        fragment = new AkunFragment();
                        break;

                }
                return loadFragment(fragment);
            }
        });
    }

    private void showActivity(Class<?> to, boolean finish){
        Intent activity = new Intent(this, to);
        startActivity(activity);
        if(finish){
            finish();
        }
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.parent_activity, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
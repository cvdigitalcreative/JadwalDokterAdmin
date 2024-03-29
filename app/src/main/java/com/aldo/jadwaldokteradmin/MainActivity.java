package com.aldo.jadwaldokteradmin;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aldo.jadwaldokteradmin.fragments.MenuAdminFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, new MenuAdminFragment(), MenuAdminFragment.class.getSimpleName())
                .commit();
    }
}

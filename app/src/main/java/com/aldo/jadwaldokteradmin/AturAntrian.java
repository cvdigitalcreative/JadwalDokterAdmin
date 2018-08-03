package com.aldo.jadwaldokteradmin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aldo.jadwaldokteradmin.R;
import com.aldo.jadwaldokteradmin.fragments.DokterAntrianFragment;

public class AturAntrian extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_antrian);

        Bundle extra = getIntent().getExtras();
        String Item = extra.getString("NamaID");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainerdokter);

        if (fragment == null) {
            fragment = new DokterAntrianFragment(Item);

            fm.beginTransaction().add(R.id.fragmentContainerdokter, fragment).commit();
        }
    }
}

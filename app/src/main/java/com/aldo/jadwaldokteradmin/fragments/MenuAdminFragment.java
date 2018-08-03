package com.aldo.jadwaldokteradmin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aldo.jadwaldokteradmin.MainActivity;
import com.aldo.jadwaldokteradmin.R;

public class MenuAdminFragment extends Fragment {
    private Button btnJadwal;
    private Button btnDataDokter;

    public MenuAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_admin, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Menu Admin");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btnJadwal = (Button)view.findViewById(R.id.btn_jadwal);
        btnDataDokter= (Button)view.findViewById(R.id.btn_datadokter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.frame_container, new InputJadwalFragment(), InputJadwalFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnDataDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.frame_container, new DataDokterFragment(), DataDokterFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}

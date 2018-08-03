package com.aldo.jadwaldokteradmin.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.aldo.jadwaldokteradmin.Login;
import com.aldo.jadwaldokteradmin.MainActivity;
import com.aldo.jadwaldokteradmin.R;
import com.aldo.jadwaldokteradmin.models.modelDokter;
import com.google.android.gms.internal.ni;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InputJadwalFragment extends Fragment {
    private String lokasi_rumah_sakit;
    private String jenis_kelamin;
    private String spesialis_temp;

    ProgressBar progressBar;
    private Spinner lokasi;
    private EditText nama;
    private Spinner spesialis;
    private EditText jadwal;
    private Button btnLogout;
    private Button btn;
    private Button btnSpesialis;
    private Spinner jenisKelamin;

    private modelDokter dokter;

    DatabaseReference reference;

    public InputJadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_input_jadwal, container, false);

        btnLogout = (Button)view.findViewById(R.id.btn_logout);
        nama = (EditText)view.findViewById(R.id.et_nama);
        spesialis = (Spinner) view.findViewById(R.id.s_spesialis);
        jadwal = (EditText)view.findViewById(R.id.et_jadwal);
        lokasi = (Spinner)view.findViewById(R.id.s_lokasi);
        btn = (Button)view.findViewById(R.id.btn_Kirim);
        btnSpesialis = view.findViewById(R.id.btn_spesialis);
        jenisKelamin = (Spinner)view.findViewById(R.id.s_jenisKelamin);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Input Jadwal Dokter");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spinerselection();
        buttonKirim();

        btnSpesialis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.tambah_spesialis, null);

                final EditText etSpesialis = (EditText)mView.findViewById(R.id.et_spesialis);
                Button btn_Tambah = (Button)mView.findViewById(R.id.btn_Tambah);
                Button btnKembali = (Button)mView.findViewById(R.id.btn_Kembali);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                dialog.show();
                dialog.setCanceledOnTouchOutside(false);

                btn_Tambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String j_spesialis = etSpesialis.getText().toString().toUpperCase();

                        if(TextUtils.isEmpty(j_spesialis)){
                            Toast.makeText(getActivity().getApplicationContext(), "Silahkan spesialis terlebih dahulu", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DatabaseReference spesialisRef = FirebaseDatabase.getInstance().getReference();
                        String key = spesialisRef.push().getKey();
                        spesialisRef.child("spesialis").child(key).child("jenis_spesialis").setValue(j_spesialis);

                        Toast.makeText(getActivity().getApplicationContext(), "Penambahan isi spesialis berhasil", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnKembali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    private void buttonKirim() {
        reference = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please Wait..");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dokter = new modelDokter(nama.getText().toString(), spesialis.getSelectedItem().toString(), jadwal.getText().toString(), lokasi.getSelectedItem().toString(), jenisKelamin.getSelectedItem().toString(), "dokter");
                        String key;
                        int status = 0;

                        for(DataSnapshot snapshot : dataSnapshot.child("dokter").getChildren()){
                            key = snapshot.getKey();

                            if(nama.getText().toString().equals(snapshot.child("nama").getValue()) && lokasi.getSelectedItem().toString().equals(snapshot.child("lokasi").getValue())){
                                dataSnapshot.child("dokter").child(key).getRef().setValue(dokter);
                                status = 1;
                            }
                        }

                        if(status == 0){
                            for(DataSnapshot snapshotSP : dataSnapshot.child("spesialis").getChildren()){
                                key = snapshotSP.getKey();

                                if(snapshotSP.child("jenis_spesialis").getValue().equals(spesialis.getSelectedItem().toString())){
                                    dataSnapshot.child("rumah_sakit").child(lokasi.getSelectedItem().toString()).child(key).child("jenis_spesialis").getRef().setValue(spesialis.getSelectedItem().toString());
                                }
                            }

                            String dokterId = reference.child("dokter").push().getKey();
                            dataSnapshot.child("dokter").child(dokterId).getRef().setValue(dokter);
                        }

                        Toast.makeText(getActivity().getApplicationContext(), "Berhasil Anda Kirim", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void spinerselection() {
        lokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lokasi_rumah_sakit = lokasi.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jenisKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenis_kelamin = jenisKelamin.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spesialis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spesialis_temp = spesialis.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> lokasi_rumah_sakit = new ArrayList<String>();
        lokasi_rumah_sakit.add("RS PELABUHAN");
        lokasi_rumah_sakit.add("RS CHARITAS");
        lokasi_rumah_sakit.add("RS MYRIA");
        lokasi_rumah_sakit.add("RS BUNDA");
        lokasi_rumah_sakit.add("RS HERMINA");
        lokasi_rumah_sakit.add("RS A.K GANI");
        lokasi_rumah_sakit.add("RS SITI KHODIJA");
        lokasi_rumah_sakit.add("RS SILOAM");
        lokasi_rumah_sakit.add("RS MUHAMMADIYAH");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, lokasi_rumah_sakit);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lokasi.setAdapter(dataAdapter);


        List<String> jenis_kelamin = new ArrayList<String>();
        jenis_kelamin.add("Pilih jenis kelamin");
        jenis_kelamin.add("laki-laki");
        jenis_kelamin.add("perempuan");

        ArrayAdapter<String> jenisKelaminAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, jenis_kelamin);

        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jenisKelamin.setAdapter(jenisKelaminAdapter);


        DatabaseReference specialistRef = FirebaseDatabase.getInstance().getReference("spesialis");

        final List<String> spesialislist = new ArrayList<String>();

        specialistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spesialislist.clear();
                spesialislist.add("Pilih spesialis");
                for(DataSnapshot spesialis_snapshot : dataSnapshot.getChildren()){
                    String spesialis_name = spesialis_snapshot.child("jenis_spesialis").getValue(String.class);
                    spesialislist.add(spesialis_name);
                }

                ArrayAdapter<String> spesialisAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spesialislist);

                spesialisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spesialis.setAdapter(spesialisAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}

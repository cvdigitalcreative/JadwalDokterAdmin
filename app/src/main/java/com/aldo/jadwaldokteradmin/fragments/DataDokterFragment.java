package com.aldo.jadwaldokteradmin.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aldo.jadwaldokteradmin.AturAntrian;
import com.aldo.jadwaldokteradmin.R;
import com.aldo.jadwaldokteradmin.models.modelDokter;
import com.aldo.jadwaldokteradmin.models.modelDokterDetail;
import com.aldo.jadwaldokteradmin.models.modelRumahSakit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataDokterFragment extends Fragment {
    ArrayList<modelDokterDetail> listItemDokterDetail = new ArrayList<>();
    RecyclerView myRecyclerView;
    Button btnTambah;

    public DataDokterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_dokter, container, false);

        btnTambah = view.findViewById(R.id.btn_tambahdata);
        myRecyclerView = view.findViewById(R.id.cv_datadokter);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        MyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (listItemDokterDetail.size() > 0 & myRecyclerView != null) {
            myRecyclerView.setAdapter(new MyAdapter(listItemDokterDetail));
        }
        myRecyclerView.setLayoutManager(MyLinearLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                View mView = layoutInflater.inflate(R.layout.tambah_dokter, null);

                final EditText etNama = mView.findViewById(R.id.et_namaDokter);
                final EditText etAlamat = mView.findViewById(R.id.et_alamatDokter);
                final Spinner sJenisKelamin = mView.findViewById(R.id.s_jeniskelamin);
                final EditText etNoTlp = mView.findViewById(R.id.et_notlpDokter);
                final CheckBox cbAkgani = mView.findViewById(R.id.cb_akgani);
                final CheckBox cbBunda = mView.findViewById(R.id.cb_bunda);
                final CheckBox cbCharitas = mView.findViewById(R.id.cb_charitas);
                final CheckBox cbHermina = mView.findViewById(R.id.cb_hermina);
                final CheckBox cbMuhammadiyah = mView.findViewById(R.id.cb_muhammadiyah);
                final CheckBox cbMyria = mView.findViewById(R.id.cb_myria);
                final CheckBox cbPelabuhan = mView.findViewById(R.id.cb_pelabuhan);
                final CheckBox cbSiloam = mView.findViewById(R.id.cb_siloam);
                final CheckBox cbSitiKhodija = mView.findViewById(R.id.cb_sitikhodija);

                Button btnSimpan = mView.findViewById(R.id.btn_Simpan);
                Button btnKembali = mView.findViewById(R.id.btn_Kembali);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                dialog.show();
                dialog.setCanceledOnTouchOutside(false);

                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("jeniskelamin"+sJenisKelamin.getSelectedItem().toString());
                        final String nama = etNama.getText().toString();
                        final String alamat = etAlamat.getText().toString();
                        final String jenisKelamin = sJenisKelamin.getSelectedItem().toString();
                        final String noTlp = etNoTlp.getText().toString();
                        final String isAkgani = cbAkgani.getText().toString();
                        final String isBunda  = cbBunda.getText().toString();
                        final String isCharitas = cbCharitas.getText().toString();
                        final String isHermina = cbHermina.getText().toString();
                        final String isMuhammdiyah = cbMuhammadiyah.getText().toString();
                        final String isMyria = cbMyria.getText().toString();
                        final String isPelabuhan = cbPelabuhan.getText().toString();
                        final String isSiloam = cbSiloam.getText().toString();
                        final String isSitiKhodija = cbSitiKhodija.getText().toString();

                        DatabaseReference reference;

                        reference = FirebaseDatabase.getInstance().getReference();

                        reference.child("datadokter").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int isAda = 0;

                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    if(snapshot.child("nama").getValue().toString().trim().equals(nama.trim())){
                                        isAda = 1;
                                        snapshot.child("nama").getRef().setValue(nama.trim());
                                        snapshot.child("alamat").getRef().setValue(alamat);
                                        snapshot.child("jeniskelamin").getRef().setValue(jenisKelamin);
                                        snapshot.child("notlp").getRef().setValue(noTlp);

                                        for(DataSnapshot RSHapussnapshot : snapshot.child("rumahsakitlainnya").getChildren()){
                                            RSHapussnapshot.child("rumahsakit").getRef().removeValue();
                                        }

                                        if(cbAkgani.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isAkgani);
                                        }

                                        if(cbBunda.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isBunda);
                                        }

                                        if(cbCharitas.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isCharitas);
                                        }

                                        if(cbHermina.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isHermina);
                                        }

                                        if(cbMuhammadiyah.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isMuhammdiyah);
                                        }

                                        if(cbMyria.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isMyria);
                                        }

                                        if(cbPelabuhan.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isPelabuhan);
                                        }

                                        if(cbSiloam.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isSiloam);
                                        }

                                        if(cbSitiKhodija.isChecked()){
                                            String key = snapshot.child("rumahsakitlainnya").getRef().push().getKey();
                                            snapshot.child("rumahsakitlainnya").child(key).child("rumahsakit").getRef().setValue(isSitiKhodija);
                                        }

                                    }
                                }

                                if(isAda == 0){
                                    String datadokterId = dataSnapshot.getRef().push().getKey();
                                    dataSnapshot.child(datadokterId).child("nama").getRef().setValue(nama.trim());
                                    dataSnapshot.child(datadokterId).child("alamat").getRef().setValue(alamat);
                                    dataSnapshot.child(datadokterId).child("jeniskelamin").getRef().setValue(jenisKelamin);
                                    dataSnapshot.child(datadokterId).child("notlp").getRef().setValue(noTlp);

                                    if(cbAkgani.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isAkgani);
                                    }

                                    if(cbBunda.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isBunda);
                                    }

                                    if(cbCharitas.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isCharitas);
                                    }

                                    if(cbHermina.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isHermina);
                                    }

                                    if(cbMuhammadiyah.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isMuhammdiyah);
                                    }

                                    if(cbMyria.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isMyria);
                                    }

                                    if(cbPelabuhan.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isPelabuhan);
                                    }

                                    if(cbSiloam.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isSiloam);
                                    }

                                    if(cbSitiKhodija.isChecked()){
                                        String rumahsakitId = dataSnapshot.child(datadokterId).child("rumahsakitlainnya").getRef().push().getKey();
                                        dataSnapshot.child(datadokterId).child("rumahsakitlainnya").child(rumahsakitId).child("rumahsakit").getRef().setValue(isSitiKhodija);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

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
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private ArrayList<modelDokterDetail> list;

        public MyAdapter(ArrayList<modelDokterDetail> Data) {
            list = Data;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item_dokterdetail, parent, false);

            MyAdapter.MyViewHolder holder = new MyAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            System.out.println("insialisasi");
            String rs = "Bekerja di ";
            holder.tvNama.setText(list.get(position).getNama());
            holder.tvAlamat.setText("Menetap di "+list.get(position).getAlamat());
            holder.tvJenisKelamin.setText(list.get(position).getJeniskelamin());
            holder.tvNoTlp.setText("no hp/telepon : "+list.get(position).getNotlp());

            for(int i=0; i<list.get(position).getRumahsakitLainnya().size(); i++){
                if(i == list.get(position).getRumahsakitLainnya().size()-1){
                    rs = rs + " dan "+list.get(position).getRumahsakitLainnya().get(i);
                }
                else{
                    rs = rs + list.get(position).getRumahsakitLainnya().get(i) + ", ";
                }
            }

            holder.tvRSLainnya.setText(rs);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tvNama;
            public TextView tvAlamat;
            public TextView tvJenisKelamin;
            public TextView tvNoTlp;
            public TextView tvRSLainnya;

            public MyViewHolder(View v) {
                super(v);
                tvNama = v.findViewById(R.id.tv_namadokter);
                tvAlamat = v.findViewById(R.id.tv_alamatdokter);
                tvJenisKelamin = v.findViewById(R.id.tv_jenisKelamin);
                tvNoTlp = v.findViewById(R.id.tv_noTlp);
                tvRSLainnya = v.findViewById(R.id.tv_rsLainnya);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void initializeList() {
        listItemDokterDetail.clear();

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("datadokter");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listItemDokterDetail.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    modelDokterDetail DokterDetail = new modelDokterDetail();

                    DokterDetail.setNama(snapshot.child("nama").getValue().toString());
                    DokterDetail.setAlamat(snapshot.child("alamat").getValue().toString());
                    DokterDetail.setJeniskelamin(snapshot.child("jeniskelamin").getValue().toString());
                    DokterDetail.setNotlp(snapshot.child("notlp").getValue().toString());
                    ArrayList<String> rumahSakit = new ArrayList<>();

                    for(DataSnapshot RSsnapshot : snapshot.child("rumahsakitlainnya").getChildren()){
                        rumahSakit.add(RSsnapshot.child("rumahsakit").getValue().toString());
                    }

                    DokterDetail.setRumahsakitlainnya(rumahSakit);
                    listItemDokterDetail.add(DokterDetail);
                }

                myRecyclerView.setAdapter(new MyAdapter(listItemDokterDetail));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

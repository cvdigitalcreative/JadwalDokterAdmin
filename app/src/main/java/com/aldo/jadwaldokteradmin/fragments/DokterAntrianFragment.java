package com.aldo.jadwaldokteradmin.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aldo.jadwaldokteradmin.R;
import com.aldo.jadwaldokteradmin.models.modelDokter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class DokterAntrianFragment extends Fragment {
    private ArrayList<modelDokter> listItemDokter = new ArrayList<>();
    private RecyclerView myRecyclerView;
    private int Images = R.mipmap.doctor;
    private DatabaseReference reference;
    private String NamaId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
    }

    public DokterAntrianFragment(String namaId) {
        this.NamaId = namaId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dokter_antrian, container, false);
        myRecyclerView = view.findViewById(R.id.cardViewDokter);
        myRecyclerView.setHasFixedSize(true);

        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        MyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (listItemDokter.size() > 0 & myRecyclerView != null) {
            myRecyclerView.setAdapter(new MyAdapter(listItemDokter));
        }
        myRecyclerView.setLayoutManager(MyLinearLayoutManager);

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private ArrayList<modelDokter> list;

        public MyAdapter(ArrayList<modelDokter> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_dokter, parent, false);

            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.namaDokter.setText(list.get(position).getNama());
            holder.Spesialist.setText(list.get(position).getSpesialis());
            holder.Jadwal.setText(list.get(position).getJadwal());
            holder.coverImage.setImageResource(Images);
            holder.coverImage.setTag(Images);

            holder.btnAtur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                    View mView = layoutInflater.inflate(R.layout.atur_antrian, null);

                    final EditText etAturAntrian = (EditText)mView.findViewById(R.id.et_jumlah_antrian);

                    Button btnSimpan = (Button)mView.findViewById(R.id.btn_Simpan);
                    Button btnKembali = (Button)mView.findViewById(R.id.btn_Kembali);
                    final Spinner sKeadaanDokter = mView.findViewById(R.id.s_keadaanDokter);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    btnSimpan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String jumlahAntrian = etAturAntrian.getText().toString();
                            final String keadaanDokter = sKeadaanDokter.getSelectedItem().toString();

                            DatabaseReference reference;

                            reference = FirebaseDatabase.getInstance().getReference("dokter");

                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        if(snapshot.child("lokasi").getValue().equals(list.get(position).getLokasi()) && snapshot.child("nama").getValue().equals(list.get(position).getNama())){
                                            snapshot.child("nomor_antrian").getRef().setValue(jumlahAntrian);
                                            snapshot.child("keadaan").getRef().setValue(keadaanDokter);
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

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView namaDokter;
            private TextView Spesialist;
            private TextView Jadwal;
            private TextView now_antrian;
            private TextView keadaan;
            private Button btnAtur;
            private ImageView coverImage;

            public MyViewHolder(View v) {
                super(v);

                coverImage = v.findViewById(R.id.coverImageViewdokter);
                namaDokter = v.findViewById(R.id.namadokter);
                Spesialist = v.findViewById(R.id.spesialist);
                Jadwal = v.findViewById(R.id.jadwal);
                now_antrian = v.findViewById(R.id.tv_antrian);
                keadaan = v.findViewById(R.id.tv_keadaan);
                btnAtur = v.findViewById(R.id.atur_antrian);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public void initializeList() {
        reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listItemDokter.clear();

                for(DataSnapshot snapshot : dataSnapshot.child("dokter").getChildren()){
                    if(snapshot.child("lokasi").getValue().equals(NamaId)){
                        modelDokter model = snapshot.getValue(modelDokter.class);
                        listItemDokter.add(model);
                    }
                }

                myRecyclerView.setAdapter(new MyAdapter(listItemDokter));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

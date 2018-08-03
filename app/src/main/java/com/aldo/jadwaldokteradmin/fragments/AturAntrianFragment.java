package com.aldo.jadwaldokteradmin.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldo.jadwaldokteradmin.AturAntrian;
import com.aldo.jadwaldokteradmin.R;
import com.aldo.jadwaldokteradmin.models.modelRumahSakit;

import java.util.ArrayList;

public class AturAntrianFragment extends Fragment {
    ArrayList<modelRumahSakit> listItemRumahSakit = new ArrayList<>();
    RecyclerView myRecyclerView;
    int Images[] = {R.mipmap.pelabuhan, R.mipmap.charitas, R.mipmap.myria, R.mipmap.bunda, R.mipmap.hermina, R.mipmap.akgani, R.mipmap.khodija, R.mipmap.siloam, R.mipmap.muhammadiyah};
    String namarumahsakit[] = {"RS PELABUHAN", "RS CHARITAS", "RS MYRIA", "RS BUNDA", "RS HERMINA", "RS AK GANI", "RS SITI KHODIJA", "RS SILOAM", "RS MUHAMMADIYAH"};

    public AturAntrianFragment() {
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
        View view = inflater.inflate(R.layout.fragment_atur_antrian, container, false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLinearLayoutManager = new LinearLayoutManager(getActivity());
        MyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (listItemRumahSakit.size() > 0 & myRecyclerView != null) {
            myRecyclerView.setAdapter(new MyAdapter(listItemRumahSakit));
        }
        myRecyclerView.setLayoutManager(MyLinearLayoutManager);

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private ArrayList<modelRumahSakit> list;

        public MyAdapter(ArrayList<modelRumahSakit> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item, parent, false);

            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.namaRumahSakit.setText(list.get(position).getNama());
            holder.coverImage.setImageResource(list.get(position).getImageResourceID());
            holder.coverImage.setTag(list.get(position).getImageResourceID());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AturAntrian.class);
                    intent.putExtra("NamaID", list.get(position).getNama());
                    startActivity(intent);
                }
            });

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView namaRumahSakit;
            public TextView alamatRumahSakit;
            public ImageView coverImage;
            public CardView cardView;

            public MyViewHolder(View v) {
                super(v);
                namaRumahSakit = (TextView) v.findViewById(R.id.namaRumahSakit);
                coverImage = (ImageView) v.findViewById(R.id.coverImageView);
                cardView = (CardView) v.findViewById(R.id.cardview);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void initializeList() {
        listItemRumahSakit.clear();

        for (int i = 0; i<9; i++) {
            modelRumahSakit item = new modelRumahSakit();
            item.setNama(namarumahsakit[i]);
            item.setImageResourceID(Images[i]);
            listItemRumahSakit.add(item);
        }
    }
}

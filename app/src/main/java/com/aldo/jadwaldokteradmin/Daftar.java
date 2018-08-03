package com.aldo.jadwaldokteradmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aldo.jadwaldokteradmin.models.modelAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Daftar extends AppCompatActivity {
    private EditText email;
    private EditText kataSandi;
    private EditText nama;
    private Button btn_daftar;
    private TextView silahkanLogin;

    private modelAdmin admin;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        klikLogin();

        klikDaftar();
    }

    private void klikDaftar() {
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("admin");

        email = (EditText)findViewById(R.id.email_lengkap_anda);
        kataSandi = (EditText)findViewById(R.id.kata_sandi);
        nama = (EditText)findViewById(R.id.nama_lengkap_anda);
        btn_daftar = (Button) findViewById(R.id.btn_daftar);

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailAdmin = email.getText().toString();
                final String kata_sandi = kataSandi.getText().toString();
                final String namaAdmin = nama.getText().toString();

                if(TextUtils.isEmpty(emailAdmin)){
                    Toast.makeText(getApplicationContext(),"Silahkan masukkan email lengkap!", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(kata_sandi)){
                    Toast.makeText(getApplicationContext(),"Silahkan masukkan kata sandi dengan benar!", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(namaAdmin)){
                    Toast.makeText(getApplicationContext(),"Silahkan masukkan nama anda!", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(emailAdmin, kata_sandi)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    admin = new modelAdmin(namaAdmin, emailAdmin);
                                    String adminId = reference.push().getKey();
                                    reference.child(adminId).setValue(admin);

                                    Toast.makeText(getApplicationContext(),"Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Daftar.this, Login.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Akun gagal dibuat", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    private void klikLogin() {
            silahkanLogin = (TextView) findViewById(R.id.silahkan_login);
            silahkanLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Daftar.this, Login.class);
                    startActivity(intent);
                }
            });

    }
}

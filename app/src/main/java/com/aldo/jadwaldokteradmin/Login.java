package com.aldo.jadwaldokteradmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private Button btn_masuk;
    private EditText email;
    private EditText kataSandi;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        klikBtnMasuk();

    }

    private void klikBtnMasuk() {
        mAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.email_lengkap_anda);
        kataSandi = (EditText)findViewById(R.id.kata_sandi);
        btn_masuk = (Button) findViewById(R.id.btn_login);

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = email.getText().toString();
                String kata_sandi = kataSandi.getText().toString();

                if(TextUtils.isEmpty(emailLogin)){
                    Toast.makeText(getApplicationContext(),"Silahkan masukkan email lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(kata_sandi)){
                    Toast.makeText(getApplicationContext(),"Silahkan masukkan kata sandi dengan benar!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailLogin, kata_sandi)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Autentikasi berhasil!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),"Autentikasi gagal!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

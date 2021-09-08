package com.kpws.kpprojectwarehousestok.activitymain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kpws.kpprojectwarehousestok.R;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton fabNotifikasi;
    public TextView tvMain, tvNamaPegawai;
    public Button btnBama, btnBaKel, btnStb, btnHome, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNamaPegawai = (TextView) findViewById(R.id.tvNamaPegawai);

        btnBama = (Button) findViewById(R.id.btnBama);
        btnBaKel = (Button) findViewById(R.id.btnBakel);
        btnStb = (Button) findViewById(R.id.btnStb);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnProfile = (Button) findViewById(R.id.btnProfile);

        fabNotifikasi = (FloatingActionButton) findViewById(R.id.fabNotifikasi);

        btnBama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BamaActivity.class);
                startActivity(intent);
            }
        });

        btnBaKel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BakalActivity.class);
                startActivity(intent);
            }
        });

        btnStb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StokActivity.class);
                startActivity(intent);
            }
        });
    }

}
package com.kpws.kpprojectwarehousestok.activitymain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kpws.kpprojectwarehousestok.Adapter.AdapterStok;
import com.kpws.kpprojectwarehousestok.R;
import com.kpws.kpprojectwarehousestok.activityall.TambahStokActivity;
import com.kpws.kpprojectwarehousestok.api.ApiClient;
import com.kpws.kpprojectwarehousestok.api.Apiinterface;
import com.kpws.kpprojectwarehousestok.model.stok.Stok;
import com.kpws.kpprojectwarehousestok.model.stok.StokBarang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokActivity extends AppCompatActivity {

    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;
    RecyclerView.LayoutManager lmStok;
    RecyclerView.Adapter adStok;
    ProgressBar pbStok;
    RecyclerView recyclerView;
    List<StokBarang> listStokBarang = new ArrayList<>();
    List<StokBarang> stokBarangs;
    EditText tanggalAwal, tanggalAkhir;
    TextView COUNT, MOUNT;
    Button btnCari;
    FloatingActionButton fabTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tanggalAkhir = (EditText) findViewById(R.id.tglAkhir);
        tanggalAwal = (EditText) findViewById(R.id.tglAwal);
        COUNT = (TextView) findViewById(R.id.tv0);
        MOUNT = (TextView) findViewById(R.id.tv1);
        btnCari = (Button) findViewById(R.id.btnCari);
        pbStok = (ProgressBar) findViewById(R.id.pb_stok);

        recyclerView = findViewById(R.id.recycler);

        fabTambah = findViewById(R.id.fab_Tambah);

        lmStok = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lmStok);
        recyclerView.setHasFixedSize(true);

        tanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        tanggalAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog1();
            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StokActivity.this, TambahStokActivity.class));
            }
        });

    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                tanggalAwal.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showDateDialog1() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                tanggalAkhir.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveStok();
    }

    public class DownloadSearchFileAsync extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }

        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }
    }

    public void retrieveStok() {
        pbStok.setVisibility(View.VISIBLE);

        Apiinterface ardStok = ApiClient.getRetrofit().create(Apiinterface.class);
        Call<Stok> tampilStok = ardStok.ardRetrieveStok();

        tampilStok.enqueue(new Callback<Stok>() {
            @Override
            public void onResponse(Call<Stok> call, Response<Stok> response) {
                listStokBarang = response.body().getData();
                System.out.println("test " + listStokBarang);
                if (listStokBarang == null) listStokBarang = new ArrayList<>();
                adStok = new AdapterStok(StokActivity.this, listStokBarang);
                recyclerView.setAdapter(adStok);
                adStok.notifyDataSetChanged();
                pbStok.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Stok> call, Throwable t) {
                pbStok.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void cariStok(String tanggal){
        Apiinterface ardCariStok = ApiClient.getRetrofit().create(Apiinterface.class);
        Call<Stok> cariStok = ardCariStok.ardCariStok(tanggal);

        cariStok.enqueue(new Callback<Stok>() {
            @Override
            public void onResponse(Call<Stok> call, Response<Stok> response) {
                pbStok.setVisibility(View.GONE);
                stokBarangs = response.body().getData();
                adStok = new AdapterStok(StokActivity.this, stokBarangs);
                recyclerView.setAdapter(adStok);
                adStok.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Stok> call, Throwable t) {
                pbStok.setVisibility(View.GONE);
                Toast.makeText(StokActivity.this, "Error on :" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
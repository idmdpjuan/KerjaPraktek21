package com.kpws.kpprojectwarehousestok.activitymain;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kpws.kpprojectwarehousestok.R;
import com.kpws.kpprojectwarehousestok.api.ApiClient;
import com.kpws.kpprojectwarehousestok.api.Apiinterface;
import com.kpws.kpprojectwarehousestok.model.data.Barang;
import com.kpws.kpprojectwarehousestok.scanbarcode.ScanCodeActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BamaActivity extends AppCompatActivity {
    public static EditText etNofa, etNoba, etKoba, etNait, etJenisItem, etTanggal, etJum, etWarna, etKet;
    Button btnSimpan, btnCameraScan;
    String nofaktur, kodebarang, namaitem, jenisitem, warna, keterangan;
    Date tanggal;
    long nobarcode;
    int jumlah;
//    DD4YouConfig generateKode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bama);

        etNofa = findViewById(R.id.etNoFa);
        etNoba = findViewById(R.id.etNoBarcode);
        etKoba = findViewById(R.id.etKoBa);
        etNait = findViewById(R.id.etNamaItem);
        etJenisItem = findViewById(R.id.etJenisItem);
        etTanggal = findViewById(R.id.etTanggal);
        etJum = findViewById(R.id.etJumlah);
        etWarna = findViewById(R.id.etWarna);
        etKet = findViewById(R.id.etKeterangan);

        btnCameraScan = findViewById(R.id.btnCameraScan);
        btnSimpan = findViewById(R.id.btnSimpan);

        datePicker();
//        generateKode = new DD4YouConfig(this);
//        etNofa.setText("TAS" + generateKode.generateUniqueID(10));

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            String barcode = data.getStringExtra("BARCODE");
                            etNoba.setText(barcode);
                        }
                    }
                });

        btnCameraScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nofaktur = etNofa.getText().toString();
                if (etNoba.getText().toString().isEmpty()) nobarcode = 0;
                else nobarcode = Long.parseLong(etNoba.getText().toString());
                kodebarang = etKoba.getText().toString();
                namaitem = etNait.getText().toString();
                jenisitem = etJenisItem.getText().toString();
                jumlah = Integer.parseInt(etJum.getText().toString());
                warna = etWarna.getText().toString();
                keterangan = etKet.getText().toString();

                if (nofaktur.trim().equals("")) {
                    etNofa.setError("No Faktur Harus Diisi");
                } else if (String.valueOf(nobarcode).trim().equals("")) {
                    etNoba.setError("No Barcode Harus Diisi");
                } else if (kodebarang.trim().equals("")) {
                    etKoba.setError("Kode Barang Harus Diisi");
                } else if (namaitem.trim().equals("")) {
                    etNait.setError("Nama Item Harus Diisi");
                } else if (jenisitem.trim().equals("")) {
                    etJenisItem.setError("Kode Barang Harus Diisi");
                } else if (String.valueOf(jumlah).trim().equals("")) {
                    etJum.setError("Jumlah Harus Diisi");
                } else if (warna.trim().equals("")) {
                    etWarna.setError("Surat Jalan Harus Diisi");
                } else if (keterangan.trim().equals("")) {
                    etKet.setError("Keterangan Harus Diisi");
                } else {
                    tambahData();
                }


            }
        });
    }

    private void datePicker() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        etTanggal.setText(currentDate);
    }

    private void tambahData() {
        Apiinterface ardBarang = ApiClient.getRetrofit().create(Apiinterface.class);

        SimpleDateFormat sdf4 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf4 = new SimpleDateFormat("YYYY-MM-dd");
        }

        tanggal = new Date();
        String tanggalString = sdf4.format(tanggal);

        Call<Barang> simpanData = ardBarang.ardTambahBarang(nofaktur, nobarcode, kodebarang, namaitem, jenisitem, tanggalString, jumlah, warna, keterangan);

        simpanData.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(BamaActivity.this, "Kode : " + kode + " | Pesan : " + pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(BamaActivity.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

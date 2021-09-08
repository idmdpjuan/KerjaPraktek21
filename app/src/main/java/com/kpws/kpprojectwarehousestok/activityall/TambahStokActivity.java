package com.kpws.kpprojectwarehousestok.activityall;

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
import com.kpws.kpprojectwarehousestok.model.stok.Stok;
import com.kpws.kpprojectwarehousestok.scanbarcode.ScanCodeActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahStokActivity extends AppCompatActivity {
    public static EditText etNoBarco, etkodB, etNamaItE, etJeItem, etTang, etJulah, etWarn;
    Button btnTam, btnCameScan;
    String kodB, namait, jeItem, warn;
    Date tang;
    long Nobarco;
    int julah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stok);

        etNoBarco = findViewById(R.id.etNoBarco);
        etkodB = findViewById(R.id.etKoB);
        etNamaItE = findViewById(R.id.etNamaItE);
        etJeItem = findViewById(R.id.etJeItem);
        etTang = findViewById(R.id.etTang);
        etJulah = findViewById(R.id.etJulah);
        etWarn = findViewById(R.id.etWarn);

        btnCameScan = findViewById(R.id.btnCameScan);
        btnTam = findViewById(R.id.btnTamb);

        datePicker();

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            String barcode = data.getStringExtra("BARCODE");
                            etNoBarco.setText(barcode);
                        }
                    }
                });

        btnCameScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        btnTam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNoBarco.getText().toString().isEmpty()) Nobarco = 0;
                else Nobarco = Long.parseLong(etNoBarco.getText().toString());
                kodB = etkodB.getText().toString();
                namait = etNamaItE.getText().toString();
                jeItem = etJeItem.getText().toString();
                julah = Integer.parseInt(etJulah.getText().toString());
                warn = etWarn.getText().toString();

                if (String.valueOf(Nobarco).trim().equals("")) {
                    etNoBarco.setError("No Barcode Harus Diisi");
                } else if (kodB.trim().equals("")) {
                    etkodB.setError("Kode Barang Harus Diisi");
                } else if (namait.trim().equals("")) {
                    etNamaItE.setError("Nama Item Harus Diisi");
                } else if (jeItem.trim().equals("")) {
                    etJeItem.setError("Kode Barang Harus Diisi");
                } else if (String.valueOf(julah).trim().equals("")) {
                    etJulah.setError("Jumlah Harus Diisi");
                } else if (warn.trim().equals("")) {
                    etWarn.setError("Surat Jalan Harus Diisi");
                } else {
                    tambData();
                }
            }
        });
    }

    private void datePicker() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        etTang.setText(currentDate);
    }

    private void tambData(){
        Apiinterface ardStok = ApiClient.getRetrofit().create(Apiinterface.class);

        SimpleDateFormat sdf4 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf4 = new SimpleDateFormat("YYYY-MM-dd");
        }

        tang = new Date();
        String tanggalString = sdf4.format(tang);

        Call<Stok> tambData = ardStok.ardTambahStok(Nobarco, kodB, namait, jeItem, julah, tanggalString, warn);

        tambData.enqueue(new Callback<Stok>() {
            @Override
            public void onResponse(Call<Stok> call, Response<Stok> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahStokActivity.this, "Kode : " + kode + " | Pesan : " + pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Stok> call, Throwable t) {
                Toast.makeText(TambahStokActivity.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
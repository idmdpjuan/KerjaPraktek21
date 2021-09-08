package com.kpws.kpprojectwarehousestok.activityall;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class UbahStokActivity extends AppCompatActivity {
    EditText  etNobarc , etKob, etNamait, etJenItem, etTangg, etJuml, etWarn;
    Button btnUbah, btnCamScan;
    String xKob, xNamaIt, xJenItem, xWa;
    String yKob, yNamaIt, yJenItem, yWa, yTangg;
    Date xTangg;
    long xNobarc, yNobarc;
    int  xJum, yJum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);


        Intent terima = getIntent();
        xNobarc = terima.getLongExtra("xNoBarcode",-1);
        xKob = terima.getStringExtra("xKodeBarang");
        xNamaIt = terima.getStringExtra("xNamaItem");
        xJenItem = terima.getStringExtra("xJenisItem");
        xJum = terima.getIntExtra("xJumlah", -1);
        xWa = terima.getStringExtra("xWarna");

        etNobarc = findViewById(R.id.etNoBarc);
        etKob = findViewById(R.id.etKoB);
        etNamait = findViewById(R.id.etNamaIt);
        etJenItem = findViewById(R.id.etJenItem);
        etTangg = findViewById(R.id.etTangg);
        etJuml = findViewById(R.id.etJumla);

        btnUbah = findViewById(R.id.btnUbah);
        btnCamScan = findViewById(R.id.btnCamScan);

        etNobarc.setText(String.valueOf(xNobarc));
        etKob.setText(xKob);
        etNamait.setText(xNamaIt);
        etJenItem.setText(xJenItem);
        etJuml.setText(xWa);
        etWarn.setText(xWa);
        etTangg.setText(String.valueOf(xTangg));

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
                            etNobarc.setText(barcode);
                        }
                    }
                });

        btnCamScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNobarc.getText().toString().isEmpty()) yNobarc = 0;
                else yNobarc = Long.parseLong(etNobarc.getText().toString());
                yKob = etKob.getText().toString();
                yNamaIt = etNamait.getText().toString();
                yJenItem = etJenItem.getText().toString();
                yJum = Integer.parseInt(etJuml.getText().toString());
                yWa = etWarn.getText().toString();
                yTangg = etTangg.getText().toString();

                updateData();
            }
        });
    }

    private void datePicker() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        etTangg.setText(currentDate);
    }

    private void updateData(){
        Apiinterface ardBarang = ApiClient.getRetrofit().create(Apiinterface.class);
        SimpleDateFormat sdf4 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf4 = new SimpleDateFormat("YYYY-MM-dd");
        }

        xTangg = new Date();
        String tanggalString = sdf4.format(xTangg);
        Log.d("JUAN", "udpateStok: " + xNobarc);
        Log.d("JUAN", "udpateStok: " + xKob);
        Log.d("JUAN", "udpateStok: " + xNobarc);
        Log.d("JUAN", "udpateStok: " + xJenItem);
        Log.d("JUAN", "udpateStok: " + tanggalString);
        Log.d("JUAN", "udpateStok: " + xJum);
        Log.d("JUAN", "udpateStok: " + xWa);

        Call<Stok> ubahData = ardBarang.ardUpdateStok(yNobarc, yKob, yNamaIt, yJenItem,
                tanggalString, yJum, yWa);

        ubahData.enqueue(new Callback<Stok>() {
            @Override
            public void onResponse(Call<Stok> call, Response<Stok> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahStokActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Stok> call, Throwable t) {
                Toast.makeText(UbahStokActivity.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
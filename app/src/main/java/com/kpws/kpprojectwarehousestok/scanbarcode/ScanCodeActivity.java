package com.kpws.kpprojectwarehousestok.scanbarcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler  {
    ZXingScannerView ScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra("BARCODE", result.getText());
        setResult(Activity.RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}

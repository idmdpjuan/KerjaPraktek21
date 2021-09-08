package com.kpws.kpprojectwarehousestok.activitymain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kpws.kpprojectwarehousestok.R;
import com.kpws.kpprojectwarehousestok.model.login.Login;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    String id, pas;
    EditText idPegawai, Password;
    ProgressDialog pDialog;
    Button btnLogin;
    Context context;
    Call<Login> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        pDialog = new ProgressDialog(context);
        idPegawai = findViewById(R.id.et_idpegawai);
        Password = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idPegawai.getText().toString();
                pas = Password.getText().toString();

                if (TextUtils.isEmpty(id)){
                    idPegawai.setError("IDPegawai Harus Diisi");
                    return;
                }
                if (TextUtils.isEmpty(pas)){
                    Password.setError("Password Harus Diisi");
                    return;
                }else {
                    LoginSistem();
                }

            }
        });
    }

    public void LoginSistem(){
        final String url = "http://192.168.1.2/appwarehouse/login.php";
        final String idp = idPegawai.getText().toString().trim();
        final String pass = Password.getText().toString().trim();
        pDialog.setMessage("Login Proses...");
        showDialog();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Data Berhasil Terhubung")) {
                    hideDialog();
                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    hideDialog();
                    Toast.makeText(context, "Idpegawai atau password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(context, "Server Tidak terhubung", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idpegawai", idp);
                params.put("password", pass);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
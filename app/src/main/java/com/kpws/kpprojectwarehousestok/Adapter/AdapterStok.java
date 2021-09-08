package com.kpws.kpprojectwarehousestok.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kpws.kpprojectwarehousestok.R;
import com.kpws.kpprojectwarehousestok.activityall.UbahStokActivity;
import com.kpws.kpprojectwarehousestok.activitymain.StokActivity;
import com.kpws.kpprojectwarehousestok.api.ApiClient;
import com.kpws.kpprojectwarehousestok.api.Apiinterface;
import com.kpws.kpprojectwarehousestok.model.stok.Stok;
import com.kpws.kpprojectwarehousestok.model.stok.StokBarang;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterStok extends RecyclerView.Adapter<AdapterStok.ViewHolder> {
    Context ctx;
    List<StokBarang> listStok;
    List<StokBarang> listStokbarang;
    String kodebarang;


    public AdapterStok(Context ctx, List<StokBarang> stok) {
        this.ctx = ctx;
        this.listStok = stok;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStok.ViewHolder holder, int position) {
        if (listStok != null && listStok.size() > 0) {
            StokBarang stokBarang = listStok.get(position);
            holder.tvKode.setText(stokBarang.getKo());
            holder.tvNob.setText(stokBarang.getKo());
            holder.tvNam.setText(stokBarang.getNam());
            holder.tvJen.setText(stokBarang.getJenis());
            holder.tvJu.setText(String.valueOf(stokBarang.getJu()));
            holder.tvWa.setText(stokBarang.getWarna());
            holder.tvTan.setText(stokBarang.getTan());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return listStok.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNob, tvKode, tvNam, tvJen, tvJu, tvWa, tvTan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNob = itemView.findViewById(R.id.tvNoB);
            tvKode = itemView.findViewById(R.id.tvKode);
            tvNam = itemView.findViewById(R.id.tvNam);
            tvJen = itemView.findViewById(R.id.tvJen);
            tvJu = itemView.findViewById(R.id.tvJu);
            tvWa = itemView.findViewById(R.id.tvWa);
            tvTan = itemView.findViewById(R.id.tvTan);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Pilih Operasi yang Akan Dilakukan");
                    builder.setTitle("Perhatian");
                    builder.setCancelable(true);

                    kodebarang = tvKode.getText().toString();

                    builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteStok();
                            dialog.dismiss();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((StokActivity) ctx).retrieveStok();
                                }
                            }, 1000);
                        }
                    });

                    builder.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getStok();
                        }
                    });
                    return false;
                }
            });
        }
    }


    private void deleteStok() {
        Apiinterface ardStok = ApiClient.getRetrofit().create(Apiinterface.class);
        Call<Stok> hapusStok = ardStok.ardDeleteStok(kodebarang);

        hapusStok.enqueue(new Callback<Stok>() {
            @Override
            public void onResponse(Call<Stok> call, Response<Stok> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ctx, "Kode : " + kode + "| Pesan : " + pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Stok> call, Throwable t) {
                Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getStok() {
        Apiinterface ardData = ApiClient.getRetrofit().create(Apiinterface.class);
        Call<Stok> getData = ardData.ardGetStok(kodebarang);

        getData.enqueue(new Callback<Stok>() {
            @Override
            public void onResponse(Call<Stok> call, Response<Stok> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listStok = response.body().getData();

                long varNo = listStokbarang.get(0).getNob();
                String varKodeBarang = listStokbarang.get(0).getKo();
                String varNamaItem = listStokbarang.get(0).getNam();
                String varJenisItem = listStokbarang.get(0).getJenis();
                int varJumlah = listStokbarang.get(0).getJu();
                String varWarna = listStokbarang.get(0).getWarna();
                String varTanggal = listStokbarang.get(0).getTan();

                Intent kirim = new Intent(ctx, UbahStokActivity.class);
                kirim.putExtra("xNoBarcode", varNo);
                kirim.putExtra("xKodeBarang", varKodeBarang);
                kirim.putExtra("xNamaItem", varNamaItem);
                kirim.putExtra("xJenisItem", varJenisItem);
                kirim.putExtra("xJumlah", varJumlah);
                kirim.putExtra("xWarna", varWarna);
                kirim.putExtra("xTanggal", varTanggal);
                ctx.startActivity(kirim);

            }

            @Override
            public void onFailure(Call<Stok> call, Throwable t) {
                Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

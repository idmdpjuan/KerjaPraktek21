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
import com.kpws.kpprojectwarehousestok.activitymain.BarangActivity;
import com.kpws.kpprojectwarehousestok.api.ApiClient;
import com.kpws.kpprojectwarehousestok.api.Apiinterface;
import com.kpws.kpprojectwarehousestok.model.data.Barang;
import com.kpws.kpprojectwarehousestok.model.data.DataBarang;
import com.kpws.kpprojectwarehousestok.model.stok.Stok;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private Context ctx;
    private List<DataBarang> listData;
    private List<DataBarang> listBarang;
    private int idBarang;

    public AdapterData(Context ctx, List<DataBarang> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_barang, parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataBarang db = listData.get(position);

        holder.tvId.setText(String.valueOf(db.getId()));
        holder.tvNoFaktur.setText(db.getNofaktur());
        holder.tvNoBarcode.setText(String.valueOf(db.getNobarcode()));
        holder.tvKodeBarang.setText(db.getKodeBarang());
        holder.tvNamaItem.setText(db.getNamaItem());
        holder.tvJenisItem.setText(db.getJenisitem());
        holder.tvTanggal.setText(String.valueOf(db.getTanggal()));
        holder.tvWarna.setText(db.getSuratJalan());
        holder.tvKeterangan.setText(db.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNoFaktur, tvNoBarcode, tvKodeBarang, tvNamaItem, tvJenisItem, tvJumlah, tvTanggal, tvWarna, tvKeterangan;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvids);
            tvNoFaktur = itemView.findViewById(R.id.tvNoFa);
            tvKodeBarang = itemView.findViewById(R.id.tvKodeBarangs);
            tvNamaItem = itemView.findViewById(R.id.tvNamaItem);
            tvJenisItem = itemView.findViewById(R.id.tvJenisItem);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvWarna = itemView.findViewById(R.id.tvWarna);
            tvKeterangan = itemView.findViewById(R.id.tvKeterangan);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilihan");
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setCancelable(true);

                    idBarang = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                        }
                    });

                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData();
                        };
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }

        public void getData(){
            Apiinterface ardData = ApiClient.getRetrofit().create(Apiinterface.class);
            Call<Barang> takeData = ardData.ardGetBarang(idBarang);

            takeData.enqueue(new Callback<Barang>() {
                @Override
                public void onResponse(Call<Barang> call, Response<Barang> response) {
                    listBarang = response.body().getData();

                    int varIdBarang = listBarang.get(0).getId();
                    String varNoFaktur = listBarang.get(0).getNofaktur();
                    long varNoBarcode = listBarang.get(0).getNobarcode();
                    String varKodeBarang = listBarang.get(0).getKodeBarang();
                    String varNamaItem = listBarang.get(0).getNamaItem();
                    String varJenisItem = listBarang.get(0).getJenisitem();
                    String vartanggal = listBarang.get(0).getTanggal();
                    int varJumlah = listBarang.get(0).getJumlah();
                    String varSuratJalan = listBarang.get(0).getSuratJalan();
                    String varKeterangan = listBarang.get(0).getKeterangan();

                    Intent kirim = new Intent(ctx, UbahStokActivity.class);
                    kirim.putExtra("xId", varIdBarang);
                    kirim.putExtra("xnofaktur", varNoFaktur);
                    kirim.putExtra("xnobarcode", varNoBarcode);
                    kirim.putExtra("xkodebarang", varKodeBarang);
                    kirim.putExtra("xnamaitem", varNamaItem);
                    kirim.putExtra("xjenisitem", varJenisItem);
                    kirim.putExtra("xtanggal", vartanggal);
                    kirim.putExtra("xjumlah", varJumlah);
                    kirim.putExtra("xsuratjalan", varSuratJalan);
                    kirim.putExtra("xketerangan", varKeterangan);
                    ctx.startActivity(kirim);

                }

                @Override
                public void onFailure(Call<Barang> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void deleteData(){
            Apiinterface ardData = ApiClient.getRetrofit().create(Apiinterface.class);
            Call<Barang> deleteData = ardData.ardDeleteBarang(idBarang);

            deleteData.enqueue(new Callback<Barang>() {
                @Override
                public void onResponse(Call<Barang> call, Response<Barang> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+kode+"| Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Barang> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

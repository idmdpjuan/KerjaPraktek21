package com.kpws.kpprojectwarehousestok.api;

import com.kpws.kpprojectwarehousestok.model.data.Barang;
import com.kpws.kpprojectwarehousestok.model.data.DataBarang;
import com.kpws.kpprojectwarehousestok.model.login.Login;
import com.kpws.kpprojectwarehousestok.model.stok.Stok;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiinterface {
    @GET("retrievelogin.php")
    Call<Login> ardLogin();

    @GET("retrieveBarang.php")
    Call<Barang> ardRetrieveBarang();

    @GET("retrieveStok.php")
    Call<Stok> ardRetrieveStok();

    @GET("searchStok.php")
    Call<Stok> ardSearchStok(@Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("createBarang.php")
    Call<Barang> ardTambahBarang(@Field("nofaktur") String nofaktur,
                                 @Field("nobarcode") long nobarcode,
                                 @Field("kodebarang") String kodebarang,
                                 @Field("namaitem") String namaitem,
                                 @Field("jenisitem") String jenisitem,
                                 @Field("tanggal") String tanggal,
                                 @Field("jumlah") int jumlah,
                                 @Field("warma") String warna,
                                 @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("createStok.php")
    Call<Stok> ardTambahStok(  @Field("nobarcode") long nobarcode,
                                 @Field("kodebarang") String kodebarang,
                                 @Field("namaitem") String namaitem,
                                 @Field("jenisitem") String jenisitem,
                                 @Field("jumlah") int jumlah,
                                 @Field("tanggal") String tanggal,
                                 @Field("warma") String warna
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<Barang> ardDeleteBarang(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("deleteStok.php")
    Call<Stok> ardDeleteStok(
            @Field("kode") String kodebarang
    );

    @FormUrlEncoded
    @POST("getdata.php")
    Call<Barang> ardGetBarang(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("searchStok.php")
    Call<Stok> ardCariStok(
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("getStok.php")
    Call<Stok> ardGetStok(
            @Field("kodeBarang") String tanggal
    );

    @FormUrlEncoded
    @POST("updateBarang.php")
    Call<Barang> ardUpdateBarang(@Field("id") int id,
                                 @Field("nofaktur") String nofaktur,
                                 @Field("nobarcode") long nobarcode,
                                 @Field("kodebarang") String kodebarang,
                                 @Field("namaitem") String namaitem,
                                 @Field("jenisitem") String jenisItem,
                                 @Field("tanggal") String tanggal,
                                 @Field("jumlah") int jumlah,
                                 @Field("warna") String warna,
                                 @Field("keterangan") String keterangan);

    @FormUrlEncoded
    @POST("updateStok.php")
    Call<Stok> ardUpdateStok(@Field("nobarcode") long nobarcode,
                             @Field("kodebarang") String kodebarang,
                             @Field("namaitem") String namaitem,
                             @Field("jenisitem") String jenisItem,
                             @Field("tanggal") String tanggal,
                             @Field("jumlah") int jumlah,
                             @Field("warna") String warna
    );
}

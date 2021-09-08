package com.kpws.kpprojectwarehousestok.model.stok;

public class StokBarang {
    private int jumlah;
    private long nobarcode;
    private String kodebarang;
    private String namaitem;
    private String jenisitem;
    private String warna;

    public int getJu() {
        return jumlah;
    }

    public void setJu(int jumlah) {
        this.jumlah = jumlah;
    }

    public long getNob() {
        return nobarcode;
    }

    public void setNob(long nobarcode) {
        this.nobarcode = nobarcode;
    }

    public String getKo() {
        return kodebarang;
    }

    public void setKo(String kodebarang) {
        this.kodebarang = kodebarang;
    }

    public String getNam() {
        return namaitem;
    }

    public void setNa(String namaitem) {
        this.namaitem = namaitem;
    }

    public String getJenis() {
        return jenisitem;
    }

    public void setJenis(String jenisitem) {
        this.jenisitem = jenisitem;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getTan() {
        return tanggal;
    }

    public void setTan(String tanggal) {
        this.tanggal = tanggal;
    }

    private String tanggal;
}
